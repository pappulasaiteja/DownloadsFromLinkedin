package com.aldrich.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aldrich.BO.LinkedinInputBO;
import com.aldrich.dao.SocialMediaLinkDAO;
import com.aldrich.entity.Category;
import com.aldrich.entity.Company;
import com.aldrich.entity.CompanyProfile;
import com.aldrich.entity.CompanyTypeMapping;
import com.aldrich.entity.Competitor;
import com.aldrich.entity.NavatarLead;
import com.aldrich.pase.vo.CompanyDomainVO;
import com.aldrich.util.PASEConstants;
@Repository
public class SocialMediaLinkDAOImpl implements SocialMediaLinkDAO 
{
	@Autowired
	private SessionFactory sessionFactory;



	@SuppressWarnings("unchecked")
	@Override
	public List<LinkedinInputBO> getLinkedinLinks() 
	{
		Query query=null;
		LinkedinInputBO linkedinInputBO=null;
		List<Object[]> objList=null;
		List<LinkedinInputBO> linkedinInputBOList=null;
		try
		{
			linkedinInputBOList=new ArrayList<LinkedinInputBO>();
			query = this.sessionFactory.openSession().createSQLQuery(
					"SELECT distinct company_url,linkedin_link FROM pase_local.social_media_links where linkedin_link!=' ';");
			objList=query.list();
			if(objList!=null && objList.size()>0)
			{
				for(Object[] list:objList)
				{
					try
					{
						linkedinInputBO=new LinkedinInputBO();
						linkedinInputBO.setLink(list[1].toString());
						linkedinInputBOList.add(linkedinInputBO);
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
		return linkedinInputBOList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NavatarLead> getNavatarLeads() 
	{
		Query query=null;
		NavatarLead navatarLead=null;
		List<Object[]> objList=null;
		List<NavatarLead> navatarLeadList=null;
		try
		{
			navatarLeadList=new ArrayList<>();
			query = this.sessionFactory.openSession().createSQLQuery(
					"SELECT domain,salesforce_link FROM pase.salesforce_leads_info where deal_stage_code not in('Stage-0') and id>515081 limit 100;");		
			objList=query.list();
			if(objList!=null && objList.size()>0)
			{
				for(Object[] list:objList)
				{
					try
					{
						navatarLead=new NavatarLead();
						navatarLead.setDomain(list[0].toString());
						navatarLead.setSalesforceLink(list[1].toString());
						navatarLeadList.add(navatarLead);
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
		return navatarLeadList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyDomainVO> getCompanyDomains() {

		List<String> list = new ArrayList<>();
		list.add("yes-tracking");
		Query query = this.sessionFactory.openSession()
				.createQuery("select model.id,model.name,model.url,model.domain,model.redirectedUrl,model.redirectedDomain from Company model where "
						+ "model.relevant in(:list)  order by model.id");
		query.setParameterList("list", list);
		query.setMaxResults(10);
		List<CompanyDomainVO> CompanyDomainList = new ArrayList<>();
		List<Object[]> objList = query.list();
		for (Object[] obj : objList) {
			try {
				if (obj.length > 1) {
					CompanyDomainVO companyDomainInfo = new CompanyDomainVO();
					companyDomainInfo.setCompanyId((Long) obj[0]);
					companyDomainInfo.setCompanyName(obj[1].toString());
					companyDomainInfo.setUrl(obj[2].toString());
					companyDomainInfo.setDomain(obj[3].toString());
					if(obj[4]!=null)
						companyDomainInfo.setRedirectURL(obj[4].toString());
					if(obj[5]!=null)
						companyDomainInfo.setRedirectedDomain(obj[5].toString());
					CompanyDomainList.add(companyDomainInfo);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return CompanyDomainList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> checkForExistanceByDomain(String domain) {
		/*List<String> list = new ArrayList<String>();
		list.add(PASEConstants.RELEVANT_YES_TRACKING);
		list.add(PASEConstants.RELEVANT_YES_NOT_TRACKING);*/
		Query query = this.sessionFactory.openSession().createQuery(
				"select model.id from Company model where model.domain = :domainName order by model.id");
		//query.setParameterList("list", list);
		query.setParameter("domainName", domain);
		return query.list();
	}
	
	
	@Override
	public Object save(Company company) {
		return this.sessionFactory.openSession().save(company);
	}


	@Override
	public List<CompanyTypeMapping> checkForExistanceByCompanyIdAndType(Long companyId, Long companyTypeId) 
	{
		Query query = this.sessionFactory.openSession().createQuery(
				"select model from CompanyTypeMapping model where model.fkCompanyId =:companyId and model.fkCompanyTypeId =:companyTypeId");
		query.setParameter("companyId", companyId);
		query.setParameter("companyTypeId", companyTypeId);
		return query.list();
	}


	@Override
	public Object saveCompanyTypeMapping(CompanyTypeMapping mapping) 
	{
		return this.sessionFactory.openSession().save(mapping);
	}


	@Override
	public List<CompanyProfile> checkForExistanceByCompanyId(Long companyId) 
	{
		Query query = this.sessionFactory.openSession()
				.createQuery("select model from CompanyProfile model where model.fkCompanyId =:companyId");
		query.setParameter("companyId", companyId);
		return query.list();
	}


	@Override
	public Object saveCompanyProfile(CompanyProfile profile) 
	{
		return this.sessionFactory.openSession().save(profile);
	}


	@Override
	public List<Category> checkForExistanceCategory(String sector) 
	{
		Query query = this.sessionFactory.openSession()
				.createQuery("select model from Category model where model.code = :categoryName");
		query.setParameter("categoryName", sector);
		return query.list();
	}


	@Override
	public Object saveCategory(Category category) 
	{
		return this.sessionFactory.openSession().save(category);
	}


	@Override
	public List<Competitor> checkExistenceCompetitior(Competitor competitor) 
	{
		Query query =null;
		try
		{
			 query = this.sessionFactory.openSession()
					.createQuery("select model from Competitor model where model.fkCompanyId =:fkCompanyId and model.fkCompetitiorCompanyId =:fkCompetitiorCompanyId");
			query.setParameter("fkCompetitiorCompanyId", competitor.getFkCompetitiorCompanyId());
			query.setParameter("fkCompanyId", competitor.getFkCompanyId());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return query.list();
	}


	@Override
	public Object saveCompetitior(Competitor competitor) 
	{
		Object obj=null;
		try
		{
			obj= this.sessionFactory.openSession().save(competitor);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return obj;
	}
	
	

}
