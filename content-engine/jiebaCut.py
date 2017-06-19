# encoding=utf8 
import sys
import os
import jieba

reload(sys) 
sys.setdefaultencoding('utf8')

num=[]
def readfile(path):
	fp = open(path, "r" )
	content = ""
	for i in fp:
		tmp = i.split(',',1)
		if len(tmp[1])>9 :
			num.append( tmp[0])
			content = content + tmp[1]
	fp.close()
	return content



content = readfile("tmp.csv")  # 读取文件内容
content = content.replace( " \n" , "" )  # 删除换行和多余的空格
content_seg = jieba.cut(content)
res = " ".join(content_seg).split('\n')
fp = open("result.csv", "w" )
fp.write("id,description\n")
for i in range(1,len(num)):
	fp.write(num[i]+",\""+res[i]+"\"\n")
#savefile("cut_res.dat", " ".join(content_seg))

fp.close()
