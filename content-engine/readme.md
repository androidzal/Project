# 基于中文内容的推荐算法引擎

## 介绍

这是一个非常简单的基于内容的推荐引擎，它根据中文文本描述计算相似性的推荐引擎。它附带了一个样例数据源文件data.csv(id，描述)，这里采集了该项目要研究的菜谱训练特征数据，id.csv是随机推荐所使用的列表，其他的csv文件都是中间生成文件。
配合提供推荐服务是一个基于flask的小型web服务，并且使用Redis存储预先计算得出来的相似度。


## 文件作用
### sendWeb.py: 
- /predict --通过GET请求传递历史ID串和相似数量，返回相似最节点。
- 能够正常工作的前提还有：
    1. Redis正常运行；
    2. Redis保存了训练过的模型；
    3. 该脚本处于运行状态。

### engines.py:
1. train:样本的模型训练方法
    1. 将分词了的数据去掉停用词（停用词库自行提供，在这里我没有使用，因为懒）
    2. 使用fit_transform对数据进行清洗，降维，提取特征（TF-IDF）
        - TF：词条词频
        -	IDF：逆向文件频率（出现该词条的文件越多，IDF越小）
    3. 使用linear_kernel将提取的特征计算词条相关性
    4. 保存前100个相似词条的ID到Redis（保存模型）
    

2. predict：通过ID在已经训练好的模型中取相似ID
    - 直接在Redis使用ZRANGE命令获取相似度排序后的N个ID。

### settings.py:Redis的配置信息
### jieba2Cut.sh:使用sed,awk去掉重复数据以及将英文引号替换为中文引号，并调用jiebaCut.py
### jiebaCut.py：使用jieba进行分词
### tests.py:测试整个引擎，可直接调用engines.py进行训练并获取到相似值
### train.py：调用engines.py进行训练并保存模型。
### getData.py:本地调用engines.py获取模型结果。

## 使用方式：
- 获取源数据,并分成“id,description”两列；
- 使用jieba2Cut.sh清洗重复数据，不良数据，并分词；
- 使用train.py进行训练模型；
- 使用"getData.py ITEM_DATA -n NUM"获取相似节点；
- 将sendWeb.py运行起来；
- 通过HTTP的GET请求方法请求服务：http://IP:5000/predict?item=A-B-C&num=10


## author
* Name:Luis
* Email:[@Luis](1396954967@qq.com)
* QQ:1396954967
* CSDN:[fjnuLuis](http://blog.csdn.net/lin_13969)
* github:[fjnuLuis](https://github.com/fjnuLuis)
