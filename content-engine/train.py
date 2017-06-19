from flask import current_app
from web import app
import unittest
import json

NUM_DATA=20
class ContentEngineTestCase(unittest.TestCase):

    def test_similar(self):
        ctx = app.test_request_context()
        ctx.push()
	

        from engines import content_engine

        #content_engine.train('sample-data.csv')
       	content_engine.train('result.csv')
	'''
        data = {'item': 56753, 'num': NUM_DATA}
        headers = [('Content-Type', 'application/json'), ('X-API-TOKEN', current_app.config['API_TOKEN'])]
        json_data = json.dumps(data)
        json_data_length = len(json_data)
        headers.append(('Content-Length', str(json_data_length)))
	print json_data
        response = app.test_client().post('http://139.199.174.96:5000/predict', headers=headers, data=json_data)
	print response
        response = json.loads(response.data)
	#fp=open("data_test.csv",'r')
	#content = fp.read()
	#fp.close()
	#cdata = content.split('\n')
	#print response
	print "search:56753"
	num=10
	if num > len(response):
		num = len(response)
	
	for i in range(0,num):
		print response[i][0],response[i][1]
        #self.assertEqual(len(response), NUM_DATA)
        #self.assertEqual(response[0][0], "19")  # Boxers are like boxers.
	'''
if __name__ == '__main__':
    unittest.main()
