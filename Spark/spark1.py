import pyspark
from pyspark.sql import SparkSession

spark = SparkSession.builder.master("local[1]")\
	.appName('MITU_Skillologies')\
	.getOrCreate()
print('Spark Version: ' , spark.version)		
