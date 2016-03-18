package net.liuxuan.springboottest.web;

import com.google.gson.Gson;
import net.liuxuan.SprKi.entity.test.model.Company;
import net.liuxuan.SprKi.entity.test.model.Person;
import net.liuxuan.SprKi.repository.test.AddressRepository;
import net.liuxuan.SprKi.repository.test.CompanyRepository;
import net.liuxuan.SprKi.repository.test.PersonRepository;
import net.liuxuan.SprKi.repository.test.TownRepository;
import net.liuxuan.SprKi.repository.test.memory.PersonMemoryRepository;
import net.liuxuan.spring.Helper.gson.EntityGsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * <p>
 * Controller for all examples contained in the sample.
 *
 * @author Thibault Duchateau
 */
@Controller
@RequestMapping(method = RequestMethod.GET)
public class SampleController {

    private static Logger log = LoggerFactory.getLogger(SampleController.class);
    @Autowired
    PersonRepository personRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    TownRepository townRepository;
    @Autowired
    CompanyRepository companyRepository;
    PersonMemoryRepository personMemoryRepository = new PersonMemoryRepository();

    /**
     * <p>
     * Populates the model with the domain objects. Used in all examples that
     * use a DOM source.
     *
     * @return a list of persons for display.
     */
//	@ModelAttribute("persons")
//	public List<Person> populateTable() {
//
//		return personMemoryRepository.findAll();
//	}
    @RequestMapping(value = "dp")
    public ModelAndView goToIndex(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.getModelMap().put("persons", personMemoryRepository.findAll());
        modelAndView.setViewName("dp");
        return modelAndView;
    }

    @RequestMapping(value = "dp1")
    public ModelAndView goToIndex1(HttpServletRequest request, ModelAndView modelAndView) {
        List<Person> all = personMemoryRepository.findAll();
        for (Person person : all) {
            townRepository.save(person.getAddress().getTown());
            addressRepository.save(person.getAddress());
            Company company = person.getCompany();
            List<Company> byName = companyRepository.findByName(company.getName());
            if (byName.size() > 0) {
                company.setId(byName.get(0).getId());
            }
            companyRepository.save(company);
            personRepository.save(person);
        }
        modelAndView.setViewName("dp");
        modelAndView.getModelMap().put("persons", personRepository.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "dp2")
    public ModelAndView goToIndex2(HttpServletRequest request, ModelAndView modelAndView) {

        Company company = new Company();
        company.setId(2L);
        modelAndView.getModelMap().put("persons", personRepository.findByCompany(company));
        modelAndView.setViewName("dp");
        return modelAndView;
    }

    @RequestMapping(value = "dp3")
    public ModelAndView goToIndex3(HttpServletRequest request, ModelAndView modelAndView) {

//		Company company = new Company();
//		company.setId(2L);
//		modelAndView.getModelMap().put("persons",personRepository.findByCompany(company));
        modelAndView.setViewName("dp");
        return modelAndView;
    }

    @RequestMapping(value = "dp4", produces = "application/json")
    public void ajaxIndex(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws IOException {
        //获取请求次数
        String draw = "0";
        draw = request.getParameter("draw");
        //数据起始位置
        String start = request.getParameter("start");
        //数据长度
        String length = request.getParameter("length");

        //总记录数
        String recordsTotal = "500";

        //过滤后记录数
        String recordsFiltered = "";
        //定义列名
        String[] cols = {"id", "LastName", "FirstName", "City", "Mail", "salary", "BirthDate", "Company"};
        //获取客户端需要那一列排序
        String orderColumn = "0";
//		orderColumn = request.getParameter("order[0][column]");
//		orderColumn = cols[Integer.parseInt(orderColumn)];
        //获取排序方式 默认为asc
        String orderDir = "asc";
        orderDir = request.getParameter("order[0][dir]");

        //获取用户过滤框里的字符
        String searchValue = request.getParameter("search[value]");

        List<Company> companies = companyRepository.findByName(searchValue);

        List<Person> personList = new ArrayList<Person>();

        for (Company company : companies) {
            personList.addAll(personRepository.findByCompany(company));
        }
        recordsFiltered = String.valueOf(personList.size());

        List rtnlist = new ArrayList();
//        Map<String,Object> data = new LinkedHashMap<String, Object>();
        for (Person person : personList) {
            Map<String,Object> data1 = new LinkedHashMap<String, Object>();
            data1.put("Id",person.getId());
            data1.put("LastName",person.getLastName());
            data1.put("FirstName",person.getFirstName());
            data1.put("City",person.getAddress().getTown().getName());
            data1.put("Mail",person.getMail());
            data1.put("Salary",person.getSalary());
            data1.put("BirthDate",person.getBirthDate());
            data1.put("Company",person.getCompany().getName());
            rtnlist.add(data1);
        }
//        for (Person person : personList) {
//            List<String> list = new ArrayList<String>();
//            list.add(String.valueOf(person.getId()));
//            list.add(person.getLastName());
//            list.add(person.getFirstName());
//            list.add(person.getAddress().getTown().getName());
//            list.add(person.getMail());
//            list.add(person.getSalary());
//            list.add(String.valueOf(person.getBirthDate()));
//            list.add(person.getCompany().getName());
//            rtnlist.add(list);
//        }

		Map<Object, Object> modelMap = new HashMap<Object, Object>();
//        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.put("data", rtnlist);
        modelMap.put("recordsTotal", recordsTotal);
        modelMap.put("recordsFiltered", recordsFiltered);
        modelMap.put("draw", draw);


        Gson gson = EntityGsonHelper.goGsonWithNoCheck();
        String json = gson.toJson(modelMap);


		response.getWriter().write(json);
        modelAndView.setViewName("test/test");

//		log.info("draw:{},start:{},length:{},search:{}",draw,start,length,search);

//		return "test/test";
//        return modelAndView;
    }

    @RequestMapping(value = "dp4A", produces = "application/json")
    public void ajaxIndexA(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws IOException {
        //获取请求次数
        String draw = "0";
        draw = request.getParameter("draw");
        //数据起始位置
        String start = request.getParameter("start");
        //数据长度
        String length = request.getParameter("length");

        //总记录数
        String recordsTotal = "500";

        //过滤后记录数
        String recordsFiltered = "";
        //定义列名
        String[] cols = {"id", "LastName", "FirstName", "City", "Mail", "salary", "BirthDate", "Company"};
        //获取客户端需要那一列排序
        String orderColumn = "0";
//		orderColumn = request.getParameter("order[0][column]");
//		orderColumn = cols[Integer.parseInt(orderColumn)];
        //获取排序方式 默认为asc
        String orderDir = "asc";
        orderDir = request.getParameter("order[0][dir]");

        //获取用户过滤框里的字符
        String searchValue = request.getParameter("search[value]");

        List<Company> companies = companyRepository.findByName(searchValue);

        List<Person> personList = new ArrayList<Person>();

        for (Company company : companies) {
            personList.addAll(personRepository.findByCompany(company));
        }
        recordsFiltered = String.valueOf(personList.size());


        Map<Object, Object> modelMap = new HashMap<Object, Object>();
//        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.put("data", personList);
        modelMap.put("recordsTotal", recordsTotal);
        modelMap.put("recordsFiltered", recordsFiltered);
        modelMap.put("draw", draw);


        Gson gson = EntityGsonHelper.goGsonWithNoCheck();
        String json = gson.toJson(modelMap);


        response.getWriter().write(json);
        modelAndView.setViewName("test/test");

//		log.info("draw:{},start:{},length:{},search:{}",draw,start,length,search);

//		return "test/test";
//        return modelAndView;
    }

    @RequestMapping(value = "dp5", produces = "application/json")
    public ModelAndView ajaxFMIndex(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws IOException {
//		RequestHelper.showParameters(request.getParameterMap());
//		Enumeration<String> parameterNames = request.getParameterNames();
//		while (parameterNames.hasMoreElements())
//		{
//			String s =parameterNames.nextElement();
//			log.trace("parameterNames:{},values:{}",s,request.getParameter(s));
//		}
        //获取请求次数
        String draw = "0";
        draw = request.getParameter("draw");
        //数据起始位置
        String start = request.getParameter("start");
        //数据长度
        String length = request.getParameter("length");

        //总记录数
        String recordsTotal = "500";

        //过滤后记录数
        String recordsFiltered = "";
        //定义列名
        String[] cols = {"id", "LastName", "FirstName", "City", "Mail", "salary", "BirthDate", "Company"};
        //获取客户端需要那一列排序
        String orderColumn = "0";
//		orderColumn = request.getParameter("order[0][column]");
//		orderColumn = cols[Integer.parseInt(orderColumn)];
        //获取排序方式 默认为asc
        String orderDir = "asc";
        orderDir = request.getParameter("order[0][dir]");

        //获取用户过滤框里的字符
        String searchValue = request.getParameter("search[value]");

        List<Company> companies = companyRepository.findByName(searchValue);

        List<Person> personList = new ArrayList<Person>();

        for (Company company : companies) {
            personList.addAll(personRepository.findByCompany(company));
        }
        recordsFiltered = String.valueOf(personList.size());

//		Map<String,Object> data = new LinkedHashMap<String, Object>();
//		for (Person person : personList) {
//			data.put("Id",person.getId());
//			data.put("LastName",person.getFirstName());
//		}


//		Map<Object, Object> info = new HashMap<Object, Object>();
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.put("data", personList);
        modelMap.put("recordsTotal", recordsTotal);
        modelMap.put("recordsFiltered", recordsFiltered);
        modelMap.put("draw", draw);


        Gson gson = EntityGsonHelper.goGsonWithNoCheck();
        String json = gson.toJson(modelMap);


//		response.getWriter().write(json);
        modelAndView.setViewName("test/test");

//		log.info("draw:{},start:{},length:{},search:{}",draw,start,length,search);

//		return "test/test";
        return modelAndView;
    }


}