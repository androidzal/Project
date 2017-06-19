use testDB;


drop table if exists label; 
CREATE TABLE label(				                    
    lno		        VARCHAR(10) NOT NULL PRIMARY KEY,	    
	labelname	    VARCHAR(100)                      
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists material; 
CREATE TABLE material(						        
	mno		        VARCHAR(10) NOT NULL PRIMARY KEY,	    
	storage1	    INT,                        
    storage2	    INT,                        
    storage3	    INT,
    materialname    VARCHAR(50),
    image           VARCHAR(100),
    url             VARCHAR(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



drop table if exists materialdetail; 
CREATE TABLE materialdetail(						        
	mno		        VARCHAR(10) NOT NULL PRIMARY KEY,	    
	authername	    VARCHAR(30),                        
    introduction    BLOB,                        
    effect  	    BLOB,
    name            VARCHAR(50),
    storage 	    BLOB,                        
    choose          BLOB,
    taboo           BLOB,
    suiTABLE        BLOB,
    makeskill       BLOB
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists materialdetail_method; 
CREATE TABLE materialdetail_method(						        
	mno		        VARCHAR(10) NOT NULL PRIMARY KEY,	                          
    recipename	    VARCHAR(50),
    picture         VARCHAR(100),
    url             VARCHAR(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists materialdetail_together; 
CREATE TABLE materialdetail_together(						        
	mno		        VARCHAR(10) NOT NULL PRIMARY KEY,	                          
    foodname	    VARCHAR(50),
    url             VARCHAR(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists recipe; 
CREATE TABLE recipe(						        
	rno		        VARCHAR(10) NOT NULL PRIMARY KEY,	    
	preparationtime	VARCHAR(30),                       
    maketime	    VARCHAR(30),                        
    mealsnumbers	VARCHAR(20),
    rname           VARCHAR(50),
    remark          BLOB,
    introduction    BLOB,
    picture         VARCHAR(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists recipelabel; 
CREATE TABLE recipelabel(				                    
    rno		        VARCHAR(10) NOT NULL ,	    
	label   	    VARCHAR(100)                      
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists recipematerial; 
CREATE TABLE recipematerial(				                    
    rno		        VARCHAR(10) NOT NULL ,	    
	material   	    VARCHAR(50),
    amount          varchar(20)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists recipestep; 
CREATE TABLE recipestep(				                    
    rno		        VARCHAR(10) NOT NULL ,
    stepno          INT,
    stepdetail      BLOB,
    steppicture     VARCHAR(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
