#coding=utf-8
import MySQLdb

conn= MySQLdb.connect(
        host='localhost',
        port = 3306,
	user='lin',
	passwd='lin',
	db ='testDB',
)
cur = conn.cursor()
cur2 = conn.cursor()
#获得表中有多少条数据
aa=cur.execute("select rno from recipe")

#打印表中的多少数据
fp = open('id.csv','w')
info = cur.fetchmany(aa)
for i in info:
	fp.write(i[0]+"\n")
cur.close()
conn.commit()
conn.close()
