# encoding=utf8 
import sys
import pandas as pd
import os
import time
import redis
from flask import current_app
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel

reload(sys) 
sys.setdefaultencoding('utf8')

def readfile(path):
    fp = open(path, "r")
    content = fp.read()
    fp.close()
    #print content.splitlines()
    return content
def info(msg):
    current_app.logger.info(msg)


class ContentEngine(object):

    SIMKEY = 'p:smlr:%s'

    def __init__(self):
        self._r = redis.StrictRedis.from_url(current_app.config['REDIS_URL'])

    def train(self, data_source):
        start = time.time()
        ds = pd.read_csv(data_source)
        info("Training data ingested in %s seconds." % (time.time() - start))

        # Flush the stale training data from redis
        self._r.flushdb()

        start = time.time()
        self._train(ds)
        info("Engine trained in %s seconds." % (time.time() - start))

    def _train(self, ds):
        """
        Train the engine.

        Create a TF-IDF matrix of unigrams, bigrams, and trigrams for each product. The 'stop_words' param
        tells the TF-IDF module to ignore common english words like 'the', etc.

        Then we compute similarity between all products using SciKit Leanr's linear_kernel (which in this case is
        equivalent to cosine similarity).

        Iterate through each item's similar items and store the 100 most-similar. Stops at 100 because well...
        how many similar products do you really need to show?

        Similarities and their scores are stored in redis as a Sorted Set, with one set for each item.

        :param ds: A pandas dataset containing two fields: description & id
        :return: Nothin!
        """

	#stop_word_list=readfile("stopwords_chinese.dat").splitlines()
        tf = TfidfVectorizer(ngram_range=(1, 1))#,analyzer='word', min_df=0)#, stop_words=stop_word_list)
        tfidf_matrix = tf.fit_transform(ds['description'])
#	print "word:",tf.vocabulary_
#	print "description:"
	#print ds["description"]
#	print "feature:"
#	print tfidf_matrix
        cosine_similarities = linear_kernel(tfidf_matrix, tfidf_matrix)
#	print "Similar:"
	#print cosine_similarities
        for idx, row in ds.iterrows():
            similar_indices = cosine_similarities[idx].argsort()[:-100:-1]
            similar_items = [(cosine_similarities[idx][i], ds['id'][i]) for i in similar_indices]

            # First item is the item itself, so remove it.
            # This 'sum' is turns a list of tuples into a single tuple: [(1,2), (3,4)] -> (1,2,3,4)
            flattened = sum(similar_items[1:], ())
            self._r.zadd(self.SIMKEY % row['id'], *flattened)

    def predict(self, item_id, num):
        """
        Couldn't be simpler! Just retrieves the similar items and their 'score' from redis.

        :param item_id: string
        :param num: number of similar items to return
        :return: A list of lists like: [["19", 0.2203], ["494", 0.1693], ...]. The first item in each sub-list is
        the item ID and the second is the similarity score. Sorted by similarity score, descending.
        """
        return self._r.zrange(self.SIMKEY % item_id, 0, num-1, withscores=True, desc=True)


content_engine = ContentEngine()
