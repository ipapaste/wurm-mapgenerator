package com.wurmonline.mapgenerator.core.util;

import java.util.function.BiFunction;

import com.wurmonline.mapgenerator.core.context.AppModule;

@AppModule("math")
public class MathUtils {
	public float[][] scale(float[][] noise, float scale)
	{
		for(int i =0; i < noise.length; i++)
			for(int j = 0; j < noise[0].length; j++)
				noise[i][j]*=scale;
		
		return noise;
	}
	
	public float[][] normalize(float[][] noise)
	{
		float biggestValue = 0;
		for(float[] sub: noise)
			for(float f : sub)
				if(biggestValue<f)
					biggestValue = f;
		
		float multiplier = 1/biggestValue;
		for(int i =0; i < noise.length; i++)
			for(int j = 0; j < noise[0].length; j++)
			{
				noise[i][j] *= multiplier;
			}
		
		return noise;
	}
	
	public float[][] operation(float[][] a1, float[][] a2, BiFunction<Float,Float,Float> function)
	{
		float[][] output = new float[a1.length][a2.length];
		for(int x=0; x < a1.length; x++)
			for(int y=0; y< a1.length; y++)
				output[x][y] = function.apply(a1[x][y], a2[x][y]);
		
		return output;
	}
	
	public float[][] sum(float[][] a1, float[][] a2)
	{
		return operation(a1,a2, (v1,v2)-> v1+v2);
	}
	
	public float[][] sub(float[][] a1, float[][] a2)
	{
		return operation(a1,a2, (v1,v2)-> v1-v2);
	}
	
	public float[][] mul(float[][] a1, float[][] a2)
	{
		return operation(a1,a2, (v1,v2)-> v1*v2);
	}
	
	public float[][] div(float[][] a1, float[][] a2)
	{
		return operation(a1,a2, (v1,v2)-> v1/v2);
	}
	
	public float[][] generateGradientMap(float[][] noise)
	{
		float[][] gradients = new float[noise.length][noise[0].length];
		
		for(int x=0; x < noise.length; x++)
			for(int y=0; y< noise[0].length; y++)
				if(x==0 || y==0)
				{
					gradients[x][y]=0f;
				}
				else
				{
					gradients[x][y]= Math.abs((noise[x][y] - noise[x-1][y])) + Math.abs(noise[x][y] - noise[x][y-1]);
				}
		return gradients;
	}
}
