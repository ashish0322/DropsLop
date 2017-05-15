# DropsLop
Web Design Final Project

The basic idea behind this project is to design and develop a new user interface for craigslist website.

Technologies and API’s Used

1) HTML 5
2) CSS 3.0
3) Bootstrap 3.0
4) Jquery
5) AngularJS 1
6) Spring Boot
7) Web Socket
8) MySql
9) Hibernate

External API: Implemented Google Maps Geo Coding API to autofill the sellers address and plot the seller’s location for the advertisement posted.

Overview 

1) Designed and developed complete back-end (Java & MySQL) which includes creation of
  i.Bean Classes which represent database entities.
 ii.Hibernate Mappings ( JPA )
iii.DAO classes (Data Access Object which interacts with the database using JPA Entity Manager to fetch the results)
 iv.Rest Services ( Which contains 40 Rest API endpoints that respond to Angular $http calls)
  v.Web Socket: Implemented web socket to enable bi-directional communication, used this feature for the comments section of the AD, to        enable real-time update of user comments on the AD.
  
2) Front End (AngularJS, HTML 5, Bootstrap, CSS, JQuery)
  i.Angular Modules 
 ii.Angular Services ( Followed singleton pattern for creating few services)
iii.Angular Directives ( Customized pagination directive)

