import java.util.*;
import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapred.*;

public class Cyber
{
	// Mapper class -> output -> string, int
	public static class LogMapper extends MapReduceBase implements Mapper <Object, Text, Text, FloatWritable> 
	{
		public void map(Object key, Text value, OutputCollector<Text, FloatWritable> Output, Reporter reporter) throws IOException
		{
			String line = value.toString();
			int add = 0;
			StringTokenizer st = new StringTokenizer(line,"\t");
			String name = st.nextToken();
		        while(st.hasMoreTokens())
			{
				add += Integer.parseInt(st.nextToken());
			}
			float avgtime = add/7.0f; // calculate average
			Output.collect(new Text(name), new FloatWritable(avgtime));
		}
	}
	// Reducer class -> string, int
	public static class LogReducer extends MapReduceBase implements Reducer<Text, FloatWritable, Text, FloatWritable> 
	{
		public void reduce(Text key, Iterator<FloatWritable> values, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException 
		{
			float val = 0.0f;
			while(values.hasNext())
			{
				if ((val=values.next().get())>5.0f)
					output.collect(key, new FloatWritable(val));
			}		
		}
	}
public static void main(String args[]) throws Exception 
	{
	JobConf conf = new JobConf(Cyber.class);
	conf.setJobName("Internet Log");
	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(FloatWritable.class);
	conf.setMapperClass(LogMapper.class);
	conf.setReducerClass(LogReducer.class);
	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class);
	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	JobClient.runJob(conf);
	}
}
