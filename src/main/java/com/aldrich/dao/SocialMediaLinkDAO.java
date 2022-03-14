package com.aldrich.dao;

import java.util.List;

import com.aldrich.BO.LinkedinInputBO;
import com.aldrich.entity.Category;
import com.aldrich.entity.Company;
import com.aldrich.entity.CompanyProfile;
import com.aldrich.entity.CompanyTypeMapping;
import com.aldrich.entity.Competitor;
import com.aldrich.entity.NavatarLead;
import com.aldrich.pase.vo.CompanyDomainVO;

public interface SocialMediaLinkDAO 
{
	public List<LinkedinInputBO> getLinkedinLinks();
	
	public List<NavatarLead> getNavatarLeads();
	
	public List<CompanyDomainVO> getCompanyDomains();
	
	public List<Long> checkForExistanceByDomain(String domain);
	
	public Object save(Company company);
	
	public List<CompanyTypeMapping> checkForExistanceByCompanyIdAndType(Long companyId, Long companyTypeId);
	
	public Object saveCompanyTypeMapping(CompanyTypeMapping mapping);
	
	public List<CompanyProfile> checkForExistanceByCompanyId(Long companyId);
	
	public Object saveCompanyProfile(CompanyProfile profile);
	
	public List<Category> checkForExistanceCategory(String sector);
	
	public Object saveCategory(Category category);
	
	public List<Competitor> checkExistenceCompetitior(Competitor competitor);
	
	public Object saveCompetitior(Competitor competitor);

}
