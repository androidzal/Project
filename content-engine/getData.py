from flask import current_app
from web import app
import unittest
import json

import argparse
parser = argparse.ArgumentParser()
parser.add_argument("item")
parser.add_argument("-n","--num")
args = parser.parse_args()


NUM,item = args.num,args.item

class ContentEngineTestCase(object):

    def test_similar(self,item,NUM_DATA):
        ctx = app.test_request_context()
        ctx.push()
	

        from engines import content_engine

        #content_engine.train('sample-data.csv')
       	#content_engine.train('result.csv')
	items=item.split('-')
	result=[]
	k={}
	for i in items:
       		data = {'item': i, 'num': int(NUM_DATA)}
        	headers = [('Content-Type', 'application/json'), ('X-API-TOKEN', current_app.config['API_TOKEN'])]
        	json_data = json.dumps(data)
        	json_data_length = len(json_data)
        	headers.append(('Content-Length', str(json_data_length)))
#	print json_data
        	response = app.test_client().post('/predict', headers=headers, data=json_data)
#		print response
        	response = json.loads(response.data)
		result = result+response
	for i,j in result:  
		if i not in k.keys():  
	            	k[i]=1
        	else:  
		        k[i]=k[i]+1
	s=sorted(k.items(),key=lambda item:item[1],reverse=True)
	#print s
	result=[]
	num=0
	for key in s:
		if key in items:
			continue
		result.append(int(key[0]))
		num = num +1
		if (num > int(NUM_DATA)):
			break
	print result
	return result
	#fp=open("data_test.csv",'r')
	#content = fp.read()
	#fp.close()
	#cdata = content.split('\n')
	#print response
#	print "search:56753"
'''
	num=10
	if num > len(response):
		num = len(response)
	
	for i in range(0,num):
		print response[i][0],response[i][1]
        #self.assertEqual(len(response), NUM_DATA)
        #self.assertEqual(response[0][0], "19")  # Boxers are like boxers.
'''
test=ContentEngineTestCase()
test.test_similar(item,NUM)
