/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import model.People;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Thomas
 */
@ManagedBean
@SessionScoped
public class MyBean {

    @Size(min = 3, max = 200, message = "Namme to short or to long")
    private String name;
    @Min(value = 0, message = "age have to be grater than 0 years")
    @Max(value = 110, message = "age have to be less than 110 years")
    private Integer age;
    @Email(message = "WRONG, this is so wrong!!!!")
    private String email;
    private List<People> listPeople;
    private EntityManager em;
    private EntityTransaction t;
    private Integer id;
    

    public List<People> getListPeople() {
        return listPeople;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void insertPeople() {
        System.out.println("Insert People");
        People p = new People();
        p.setAge(age);
        p.setEmail(email);
        p.setName(name);
        t = em.getTransaction();
        t.begin();
        em.persist(p);
        t.commit();
        clear();
    }

    public void listBy(boolean orderByName) {
        if (orderByName) {
            listPeople = em.createNamedQuery("People.getAllByName").getResultList();
        } else {
            listPeople = em.createNamedQuery("People.getAllByAge").getResultList();
        }
    }
    public String prepareUpdate (People p){
        this.age = p.getAge();
        this.email = p.getEmail();
        this.name = p.getName();
        this.id = p.getId();
        return "submit";
    }
    
    public String update(){
        t= em.getTransaction();
        t.begin();
        People myPeople = em.find(People.class, id);
        myPeople.setAge(age);
        myPeople.setEmail(email);
        myPeople.setName(name);
        em.persist(myPeople);
        t.commit();
        this.clear();
        return "back";
    }
    public void deletePeople(People p){
        System.out.println("delete people: "+p.getName());
        t = em.getTransaction();
        t.begin();
        em.remove(p);
        t.commit();
    }
    private void clear(){
        this.age = null;
        this.email = null;
        //this.id = null;
        this.name = null;
    }
    /**
     * Creates a new instance of MyBean
     */
    public MyBean() {
        em = Persistence.createEntityManagerFactory("Lab3PU").createEntityManager();
    }
}
