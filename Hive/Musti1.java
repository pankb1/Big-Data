import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
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
                  
public class Musti1{
	// Mapper class -> output -> string, int
	public static class Musti1Mapper extends Mapper<Object, Text, Text, FloatWritable> {
	
	
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException{
		String line[] = value.toString().split("\\s+");
		float maxTemp =Float.parseFloat(line[5]);
		String year = line[1].substring(0,4);
		if(maxTemp > -60.0f && maxTemp < 60.0f)
			context.write(new Text(year), new FloatWritable(maxTemp));
		}
	}

	// Reducer class -> string, int
	public static class Musti1Reducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
		public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
			float max = -100;
			
			for(FloatWritable temp : values) {
				if(temp.get() > max)
					max = temp.get();
			}
			context.write(key, new FloatWritable(max));
		}
	}
	public static void main(String args[]) throws Exception
	{
		// create the object of Configuration class
		Configuration conf = new Configuration();
	
		//create the object of job class
		Job job = new Job(conf, "Hottest Year");
	
		// set the data type of output key
		job.setOutputKeyClass(Text.class);
	
		// Set the data type of output value
		job.setOutputValueClass(IntWritable.class);
	
		// Set the data format of output
		job.setOutputFormatClass(TextOutputFormat.class);
	
		//set the data format of input
		job.setInputFormatClass(TextInputFormat.class);
	
		//set the name of Mapper class
		job.setMapperClass(WordMapper.class);
	
		//set the name of Reducer class
		job.setReducerClass(WordReducer.class);
	
		//set the input files path from 0th argument
		FileInputFormat.addInputPath(job, new Path(args[0]));
	
		//set the output files path from 1st arg
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
	
		//Execute the job and wait for completion		
		job.waitForCompletion(true);
	}
}
