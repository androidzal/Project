from flask import Flask,request
import os
import random
#from ttt import test

app = Flask(__name__)
dat=[]

@app.route('/predict')
def predict():
	item=request.args.get('item','')
	num=request.args.get('num','')
	if not num :
		num = '30'
	if not item :
		res = []
		for i in range(0,int(num)):
			res.append(int(random.choice(dat)))
		print res
		return str(res)
	return os.popen('python2.7 getData.py '+item+" -n "+num).read()
	#return test.test_similar(item,num)

if __name__ == '__main__':
	fp = open('id.csv','r')
	for i in fp:
		i = i.split('\n')
		dat.append(i[0])
	app.run(host='0.0.0.0')
