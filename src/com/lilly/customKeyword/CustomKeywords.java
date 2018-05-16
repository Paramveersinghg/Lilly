package com.lilly.customKeyword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;

public class CustomKeywords implements KeywordLibrary{

	public FunctionResult FindTextRowInFile(String csvFilePath, String SearchString, Boolean IgnoreCase, Integer StartAtLine)
			throws Exception
	{

		BufferedReader br = null;
		String line,outputMessage = "";
		int linenumber = 0;

		List<String> inputList = new ArrayList<String>();

		try
		{  	
			br = new BufferedReader(new FileReader(csvFilePath));


			while ((line = br.readLine()) != null) 
			{
				inputList.add(line);			
			}

			for(int i = StartAtLine-1; i < inputList.size() ; i++)	
			{
				if(IgnoreCase)
				{
					if(inputList.get(i).toLowerCase().contains(SearchString.toLowerCase()))
					{
						linenumber=i+1;
						System.out.println("line : "+linenumber);
						outputMessage = "String found at line : "+ linenumber;
						break;
					}					
					else
					{
						outputMessage = "String not found after line : "+ StartAtLine ;
					}
				}
				else
				{
					if(inputList.get(i).contains(SearchString))
					{
						linenumber=i+1;
						System.out.println("line : "+linenumber);
						outputMessage = "String found at line : "+ linenumber;
						break;
					}
					else
					{
						if(inputList.get(i).toLowerCase().contains(SearchString.toLowerCase()))
						{
							outputMessage = "String not match due to case sensitivity";	
							return Result.FAIL(ResultCodes.ERROR_TEXT_NOT_FOUND).setMessage(outputMessage).make();
						}
						outputMessage = "String not found after line : "+ StartAtLine ;
					}	
				}
			}
			
			return Result.PASS().setMessage(outputMessage).setOutput(linenumber).make();
		}
		catch(Exception e)
		{
			return Result.FAIL(ResultCodes.ERROR_TEXT_NOT_FOUND).setMessage(e.getMessage()).make();
		}
		finally
		{
			br.close();
		}


	}

	public FunctionResult GetValueFromCSV(String csvFilePath, Integer rowNumber, Integer commaIndex)
			throws Exception
	{

		BufferedReader br = null;
		String line,CSVString,valueatIndex = "";
		List<String> inputList = new ArrayList<String>();

		try
		{  	
			br = new BufferedReader(new FileReader(csvFilePath));


			while ((line = br.readLine()) != null) 
			{
				inputList.add(line);			
			}

			CSVString = inputList.get(rowNumber-1);
			String[] commaSeperator = CSVString.split(",");

			valueatIndex = commaSeperator[commaIndex.intValue()];
			return Result.PASS().setMessage("Value at row " + rowNumber + " and Index " + commaIndex + " is " + valueatIndex + "]").setOutput(valueatIndex).make();
		}
		
		catch(Exception e)
		{
			return Result.FAIL(ResultCodes.ERROR_TEXT_NOT_FOUND).setMessage(e.getMessage()).make();
		}
		
		finally
		{
			br.close();
		}

	}

	public FunctionResult Custom_GetStringLength(String CompleteString)
			throws Exception
	{	
		try
		{  	
			
			if(CompleteString.equals(null) || CompleteString.equals(""))
			{
				return Result.PASS().setMessage("String is Blank or Null").setOutput(0).make();
			}
			else
			{
				return Result.PASS().setMessage("String Length is : " + CompleteString.length()).setOutput(CompleteString.length()).make();
			}
			
			
		}
		
		catch(Exception e)
		{
			return Result.FAIL(ResultCodes.ERROR_TEXT_NOT_FOUND).setMessage(e.getMessage()).make();
		}
		
	}
		
	}

	
	

