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
aa=cur.execute("select rno,rname,introduction,remark from recipe")

#打印表中的多少数据
fp = open('aaa.csv','w')
info = cur.fetchmany(aa)
for i in info:
	fp.write(i[0]+","+i[1]+","+i[2]+","+i[3]+",")
	bb = cur2.execute("select Group_concat(label) from recipelabel where rno='"+i[0]+"'")
	info2 = cur2.fetchmany(bb)
	if info2[0][0]:
		fp.write(info2[0][0]+",")


	bb = cur2.execute("select Group_concat(stepdetail) from recipestep where rno='"+i[0]+"'")
	info2 = cur2.fetchmany(bb)
	if info2[0][0]:
		fp.write(info2[0][0]+",")


	bb = cur2.execute("select Group_concat(material) from recipematerial where rno='"+i[0]+"'")
	info2 = cur2.fetchmany(bb)
	if info2[0][0]:
		fp.write(info2[0][0]+"\n")

cur.close()
conn.commit()
conn.close()
