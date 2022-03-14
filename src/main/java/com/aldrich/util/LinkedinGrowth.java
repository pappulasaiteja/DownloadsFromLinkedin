package com.aldrich.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkedinGrowth 
{
	public static void main(String[] args) throws IOException 
	{
		
		/*LinkedinGrowth growth=new LinkedinGrowth();
		File file1 = new File("C:/Users/akumar/Desktop/Linkedin/08.02.2019/");
		File [] files = file1.listFiles();
		for(File f:files)
		{
			try
			{
				Double size=(double) f.length() / 1024;
				int fileSize=size.intValue();
				if(fileSize>224 && fileSize<500)
				{
					File file=new File(f.getPath());
					if(file.exists())
					{
						long companyId=Long.parseLong(file.getPath().replace("C:\\Users\\akumar\\Desktop\\Linkedin\\08.02.2019\\", "").replace(".html", ""));

						//System.out.println(companyId);
						if(companyId==177606)
						{
							Document doc=Jsoup.parse(file, null);
							if(!doc.toString().contains("Insights not available"))
							{
								Elements ele=doc.select("table");
								Element tableEle=null;
								int eleSize=ele.size();
								System.out.println(eleSize);
								for(int i=0;i<ele.size();i++)
								{
									try
									{
										if(i==0)
										{
											tableEle=(Element)ele.get(i);
											if(tableEle!=null)
											{
												//growth.getHeadCountGrowth(tableEle,companyId);
											}
										}
										else if(i==1)
										{
											tableEle=(Element)ele.get(i);
											if(tableEle!=null)
											{
												//growth.getEmployeeCountMonthwise(tableEle);
											}
										}
										else if(i==3)
										{
											tableEle=(Element)ele.get(i);
											if(tableEle!=null)
											{
												Elements tbodyTrEle=null;
												Elements tdEle=null;
												//System.out.println(tableEle.toString());
												
												tbodyTrEle=tableEle.select("tbody tr");
												if(tbodyTrEle!=null)
												{
													for(int j=1;j<tbodyTrEle.size();j++)
													{
														try
														{
															tdEle=tbodyTrEle.get(j).select("td");
															if(tdEle!=null)
															{
																String functionName=tdEle.get(0).text();
																String numberOfEmployees=tdEle.get(1).text();
																String totalHeadCountPercent=tdEle.get(2).text();
																String sixmonth=tdEle.get(3).text();
																String oneYear=tdEle.get(4).text();
															System.out.println("Function Name:"+functionName+"--"+"Employee:"+numberOfEmployees+"--"+"totalHeadCounts:"+totalHeadCountPercent+"--"+"Sixmonth:"+sixmonth+"--"+"OneYear:"+oneYear);	
																
															}
														}
														catch(Exception e)
														{
															e.printStackTrace();
														}
													}
												}
												//System.out.println(trtd.toString());
											}
										}
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
									//System.out.println((Element)ele.get(i));
								}
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		int i=files.length;
		System.out.println(i);

	}




	public void getHeadCountGrowth(Element tableEle,long companyId)
	{
		try
		{
			Elements trEle=tableEle.select("tr td");
			String employeeCount=trEle.get(0).text();
			String sixMonthsGrowth=trEle.get(1).text();
			if(sixMonthsGrowth.contains("No change"))
				sixMonthsGrowth=sixMonthsGrowth.substring(0,sixMonthsGrowth.lastIndexOf("%"));
			else
				sixMonthsGrowth=sixMonthsGrowth.substring(sixMonthsGrowth.indexOf("%")+1,sixMonthsGrowth.lastIndexOf("%"));
			String oneYearGrowth=trEle.get(2).text();
			if(oneYearGrowth.contains("No change"))
				oneYearGrowth=oneYearGrowth.substring(0,oneYearGrowth.lastIndexOf("%"));
			else
				oneYearGrowth=oneYearGrowth.substring(oneYearGrowth.indexOf("%")+1,oneYearGrowth.lastIndexOf("%"));
			String twoYearGrowth=trEle.get(3).text();
			if(twoYearGrowth.contains("No change"))
				twoYearGrowth=twoYearGrowth.substring(0,twoYearGrowth.lastIndexOf("%"));
			else
				twoYearGrowth=twoYearGrowth.substring(twoYearGrowth.indexOf("%")+1,twoYearGrowth.lastIndexOf("%"));
			System.out.println("EmployeeCount:"+employeeCount+"--"+"6mGrowth:"+sixMonthsGrowth.trim()+"--"+"oneYearGrowth:"+oneYearGrowth.trim()+"--"+"2yearGrowth:"+twoYearGrowth.trim()+"--"+"companyId:"+companyId);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}



	public void getEmployeeCountMonthwise(Element tableEle)
	{
		try
		{
			Elements trEle=tableEle.select("tbody tr");
			//System.out.println(trEle.toString());
			for(int i=0;i<trEle.size();i++)
			{
				Element tr=trEle.get(i);
				Elements subtr=tr.select("td");
				String date=subtr.get(0).text();
				String[] monthYear=date.split(" ");
				String month=monthYear[0];
				String year=monthYear[1];
				String count=subtr.get(1).text();
				System.out.println(month+"  "+year+"  "+count);
				System.out.println("Date:"+subtr.get(0).text()+" -- "+"EmployeeCount:"+subtr.get(1).text());
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
		
		
		
		/*Document doc=Jsoup.parse("C:/LinkedinData/217653.html", "UTF-8");
		Elements ele=doc.select("table");
		//Element tableEle=null;
		int eleSize=ele.size();
		System.out.println(eleSize);
		
		
		LinkedinGrowth growth=new LinkedinGrowth();
		File file1 = new File("C:/Users/akumar/Desktop/Linkedin/08.02.2019/");
		File [] files = file1.listFiles();
		for(File f:files)
		{
			try
			{
				Double size=(double) f.length() / 1024;
				int fileSize=size.intValue();
				if(fileSize>224 && fileSize<500)
				{
					File file=new File(f.getPath());
					if(file.exists())
					{
						long companyId=Long.parseLong(file.getPath().replace("C:\\Users\\akumar\\Desktop\\Linkedin\\08.02.2019\\", "").replace(".html", ""));

						//System.out.println(companyId);
						if(companyId==177606)
						{
							Document doc=Jsoup.parse(file, null);
							if(!doc.toString().contains("Insights not available"))
							{
								Elements ele=doc.select("table");
								Element tableEle=null;
								int eleSize=ele.size();
								System.out.println(eleSize);
								for(int i=0;i<ele.size();i++)
								{
									try
									{
										if(i==0)
										{
											tableEle=(Element)ele.get(i);
											if(tableEle!=null)
											{
												//growth.getHeadCountGrowth(tableEle,companyId);
											}
										}
										else if(i==1)
										{
											tableEle=(Element)ele.get(i);
											if(tableEle!=null)
											{
												//growth.getEmployeeCountMonthwise(tableEle);
											}
										}
										else if(i==3)
										{
											tableEle=(Element)ele.get(i);
											if(tableEle!=null)
											{
												Elements tbodyTrEle=null;
												Elements tdEle=null;
												//System.out.println(tableEle.toString());
												
												tbodyTrEle=tableEle.select("tbody tr");
												if(tbodyTrEle!=null)
												{
													for(int j=1;j<tbodyTrEle.size();j++)
													{
														try
														{
															tdEle=tbodyTrEle.get(j).select("td");
															if(tdEle!=null)
															{
																String functionName=tdEle.get(0).text();
																String numberOfEmployees=tdEle.get(1).text();
																String totalHeadCountPercent=tdEle.get(2).text();
																String sixmonth=tdEle.get(3).text();
																String oneYear=tdEle.get(4).text();
															System.out.println("Function Name:"+functionName+"--"+"Employee:"+numberOfEmployees+"--"+"totalHeadCounts:"+totalHeadCountPercent+"--"+"Sixmonth:"+sixmonth+"--"+"OneYear:"+oneYear);	
																
															}
														}
														catch(Exception e)
														{
															e.printStackTrace();
														}
													}
												}
												//System.out.println(trtd.toString());
											}
										}
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
									//System.out.println((Element)ele.get(i));
								}
							}
						}
					}
				//}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		int i=files.length;
		System.out.println(i);

	}




	public void getHeadCountGrowth(Element tableEle,long companyId)
	{
		try
		{
			Elements trEle=tableEle.select("tr td");
			String employeeCount=trEle.get(0).text();
			String sixMonthsGrowth=trEle.get(1).text();
			if(sixMonthsGrowth.contains("No change"))
				sixMonthsGrowth=sixMonthsGrowth.substring(0,sixMonthsGrowth.lastIndexOf("%"));
			else
				sixMonthsGrowth=sixMonthsGrowth.substring(sixMonthsGrowth.indexOf("%")+1,sixMonthsGrowth.lastIndexOf("%"));
			String oneYearGrowth=trEle.get(2).text();
			if(oneYearGrowth.contains("No change"))
				oneYearGrowth=oneYearGrowth.substring(0,oneYearGrowth.lastIndexOf("%"));
			else
				oneYearGrowth=oneYearGrowth.substring(oneYearGrowth.indexOf("%")+1,oneYearGrowth.lastIndexOf("%"));
			String twoYearGrowth=trEle.get(3).text();
			if(twoYearGrowth.contains("No change"))
				twoYearGrowth=twoYearGrowth.substring(0,twoYearGrowth.lastIndexOf("%"));
			else
				twoYearGrowth=twoYearGrowth.substring(twoYearGrowth.indexOf("%")+1,twoYearGrowth.lastIndexOf("%"));
			System.out.println("EmployeeCount:"+employeeCount+"--"+"6mGrowth:"+sixMonthsGrowth.trim()+"--"+"oneYearGrowth:"+oneYearGrowth.trim()+"--"+"2yearGrowth:"+twoYearGrowth.trim()+"--"+"companyId:"+companyId);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}



	public void getEmployeeCountMonthwise(Element tableEle)
	{
		try
		{
			Elements trEle=tableEle.select("tbody tr");
			//System.out.println(trEle.toString());
			for(int i=0;i<trEle.size();i++)
			{
				Element tr=trEle.get(i);
				Elements subtr=tr.select("td");
				String date=subtr.get(0).text();
				String[] monthYear=date.split(" ");
				String month=monthYear[0];
				String year=monthYear[1];
				String count=subtr.get(1).text();
				System.out.println(month+"  "+year+"  "+count);
				System.out.println("Date:"+subtr.get(0).text()+" -- "+"EmployeeCount:"+subtr.get(1).text());
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/


		
		/*Long companyId=0l;
		List<Long> companyList=new ArrayList<>();
		File folder = new File("C:\\LinkedinData\\");
		File [] files = folder.listFiles();
		for(File file:files)
		{
			try
			{
				File filePath=new File(file.getPath());
				if(filePath!=null && filePath.exists())
				{
					companyId=Long.parseLong(file.getPath().replace("C:\\LinkedinData\\", "").replace(".html", ""));
					companyList.add(companyId);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		File file=new File("C:\\Users\\akumar\\Desktop\\ids.txt");
		//FileWriter writer = new FileWriter("C:\\Users\\akumar\\Desktop\\discharge-1-2-3---callibra-inc.html");
		FileWriter writer = new FileWriter(file);
		for(Long id:companyList)
		{
			System.out.println(id);
			writer.write(id.toString()+""+"\n");
			
		}
		writer.close();*/
		
		
		


	//}
		
		
		Long companyId=0l;
		List<Long> companyList=new ArrayList<>();
		File folder = new File("C:\\Linkedin Insights 2020\\March\\03.09.2020\\Valid\\");
		File [] files = folder.listFiles();
		for(File file:files)
		{
			try
			{
				File filePath=new File(file.getPath());
				if(filePath!=null && filePath.exists())
				{
					companyId=Long.parseLong(file.getPath().replace("C:\\Linkedin Insights 2020\\March\\03.09.2020\\Valid\\", "").replace(".html", ""));
					companyList.add(companyId);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		File file=new File("C:\\Users\\akumar\\Desktop\\ids1.txt");
		//FileWriter writer = new FileWriter("C:\\Users\\akumar\\Desktop\\discharge-1-2-3---callibra-inc.html");
		FileWriter writer = new FileWriter(file);
		for(Long id:companyList)
		{
			System.out.println(id);
			writer.write(id.toString()+""+"\n");
			
		}
		writer.close();

	}

}
