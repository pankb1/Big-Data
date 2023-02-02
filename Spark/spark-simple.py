import pyspark
from pyspark.sql import SparkSession

spark = SparkSession.builder.master("local[1]")\
	.appName('MITU_Skillologies')\
	.getOrCreate()
#print('Spark Version: ' , spark.version)	


#creating dataframe
#df = spark.createDataFrame ([("Scala", 25000),("Spark",35000),("PHP",21000)])
#print(df.show())


# spark context object
sc = spark.sparkContext
#print(sc)
#print("Spark App Name: ", sc.appName)

# create RDD function
#rdd = spark.sparkContext.range(1,5)
#print('RDD Contents: ',rdd.collect())

#create RDD from parallelize
#data = [1,2,3,4,5,6,7,8,9.10,11,12] # list(range(1,13))
#rdd = sc.parallelize(data)
#print(rdd.collect())

#create RDD from parallelize
#rdd = sc.textFile('/home/student/fruits.txt')
#print(rdd.collect())

#create RDD from file
#rdd = sc.textFile('fruits1.txt,fruits.txt') # Dataset/*
#print(rdd.collect())

#create RDD from file
#rdd = sc.textFile('fruits.txt')
#print(rdd.collect())
#print('1. Number of partitions: ', rdd.getNumPartitions())

#Create RDD from list
#rdd = sc.parallelize([1,2,3,4,5,6,7,8])
#print(rdd.collect())
#print('2. Number of partitions: ', rdd.getNumPartitions())


#Create RDD from list
rdd = sc.parallelize([1,2,3,4,5,6,7,8])
print(rdd.collect())
print('Number of partitions: ', rdd.getNumPartitions())
rdd2 = rdd.repartition(2)
print('Number of partitions: ', rdd2.getNumPartitions())




