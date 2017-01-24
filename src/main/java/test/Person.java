package test;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  test.Person
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/1/16 10:55
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/1/16  |    Moses       |     Created
 */
public class Person {
    private String firstName, lastName, job, gender;
    private int salary, age;

    public Person(String firstName, String lastName, String job,
                  String gender, int age, int salary)       {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.job = job;
        this.salary = salary;
    }

    public static void main(String[] args) {
        List<Person> javaProgrammers = new ArrayList<Person>() {
            {
                add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
                add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
                add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
                add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
                add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
                add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
                add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
                add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
                add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2100));
                add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
            }
        };
        //javaProgrammers.forEach((t)-> System.out.printf("%s %d \r\n",t.firstName,t.salary));
        javaProgrammers.stream()
                .filter((t)->t.salary >1500)
                .sorted((t,t1)->t1.salary-t.salary)
                .forEach((t)-> System.out.printf("%s %d \r\n",t.firstName,t.salary));
    }
}
