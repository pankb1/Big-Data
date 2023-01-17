import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.StringTokenizer;
import java.io.IOException;
                  
public class Ages{
	// Mapper class -> output -> string, int
	public static class AgeMapper extends Mapper<Object, Text, Text, IntWritable> {
	 	boolean isNumber(String s)
	 	{
		for(int i=0; i<s.length(); i++)
		{
			char ch = s.charAt(i);
			if(ch< '0' || ch>'9')
				return false;
		}
		return true;
		}
		Text word = new Text();  //Output key value
	
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException{
		String line = value.toString();
		StringTokenizer s=new StringTokenizer(line);
		while(s.hasMoreTokens())  {
			String word = s.nextToken();
			if(isNumber(word))
			{
			int no=Integer.parseInt(word);
				context.write(new Text("age"), new IntWritable(no) );
				}
			/*
			String token = s.nextToken();
			word.set(token);
			context.write(word, new IntWritable(1));*/
		}
	}}
	// Reducer class -> string, int
	public static class AgeReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			IntWritable addition = new IntWritable();
			FloatWritable avg=new FloatWritable();
			int sum = 0, total = 0;
			for(IntWritable num : values) {
				sum = sum + num.get();
				total++;
			}
			avg.set((float)sum/total);
			context.write(new Text("Average is :"), avg);
		}
	}
	public static void main(String args[]) throws Exception
	{
		// create the object of Configuration class
		Configuration conf = new Configuration();
	
		//create the object of job class
		Job job = new Job(conf, "Ages");
	
		// set the data type of output key
		job.setMapOutputKeyClass(Text.class);
	
		// Set the data type of output value
		job.setMapOutputValueClass(IntWritable.class);
		
		// set the data type of output key
		job.setOutputKeyClass(Text.class);
	
		// Set the data type of output value
		job.setOutputValueClass(FloatWritable.class);
	
		// Set the data format of output
		job.setOutputFormatClass(TextOutputFormat.class);
	
		//set the data format of input
		job.setInputFormatClass(TextInputFormat.class);
	
		//set the name of Mapper class
		job.setMapperClass(AgeMapper.class);
	
		//set the name of Reducer class
		job.setReducerClass(AgeReducer.class);
	
		//set the input files path from 0th argument
		FileInputFormat.addInputPath(job, new Path(args[0]));
	
		//set the output files path from 1st arg
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
	
		//Execute the job and wait for completion 
		job.waitForCompletion(true);
	}		
}

