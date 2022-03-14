package com.aldrich.service.impl;

import java.io.File;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aldrich.dao.SocialMediaLinkDAO;
import com.aldrich.entity.SocialMediaLink;
import com.aldrich.service.UpdateLinkedinUniqueIdService;
@Service
public class UpdateLinkedinUniqueIdServiceImpl implements UpdateLinkedinUniqueIdService 
{
	@Autowired
	SocialMediaLinkDAO socialMediaLinkDAO;
	

	@Override
	public void runService() 
	{
		Long companyId=0l;
		SocialMediaLink link=null;
		try
		{
			File folder = new File("C:\\Users\\akumar\\Desktop\\Linkedin Data\\Linkedin AboutUs\\");
			File [] files = folder.listFiles();
			if(files.length>0)
			{
				for(File file:files)
				{
					try
					{
						File filePath=new File(file.getPath());
						Document doc=Jsoup.parse(new File(filePath.getPath()), "UTF-8");
						companyId=Long.parseLong(file.getPath().replace("C:\\Users\\akumar\\Desktop\\Linkedin Data\\Linkedin AboutUs\\", "").replace(".html", ""));
						String uniqueId=doc.select("a[data-control-name=topcard_overflow_share_page_button]").attr("href");
						if(!uniqueId.equals(""))
						{
							uniqueId=uniqueId.replace("/messaging/compose/?body=https%3A%2F%2Fwww.linkedin.com%2Fcompany%2F", "");	
						}
						 link=new SocialMediaLink();
						 //link.setFkCompanyId(companyId);
						 link.setUniqueId(uniqueId);
						 link.setFkLinkTypeInfoId(4l);
						 link.setActivityDateTime(new Date());
						 if(link!=null)
						 {
							 //this.socialMediaLinkDAO.UpdateSocialMediaLink(companyId, link);
						 }
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
