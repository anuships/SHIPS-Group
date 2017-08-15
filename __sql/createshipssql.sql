use shipsdb;
	
    
	CREATE TABLE IF NOT EXISTS users(UID VARCHAR(60), USERNAME VARCHAR(30),
                    EMAIL VARCHAR(60), PASSWORD VARCHAR(30), SALT VARCHAR(60), FIRST_NAME VARCHAR(30), LAST_NAME VARCHAR(30), PRIMARY KEY(UID))ENGINE=InnoDB DEFAULT CHARSET=utf8;
                    
	CREATE TABLE IF NOT EXISTS module_category(CID INTEGER PRIMARY KEY AUTO_INCREMENT,TITLE VARCHAR(50))ENGINE=InnoDB DEFAULT CHARSET=utf8;
            
	INSERT INTO module_category (TITLE) VALUES("SELF-ASSESSMENT"), ("FACTSHEET"), ("THERAPEUTIC TOOLS"), ("TREATMENT");
 	DROP TABLE IF EXISTS modules;
	CREATE TABLE IF NOT EXISTS modules(MID INTEGER PRIMARY KEY AUTO_INCREMENT,CID INTEGER, TITLE VARCHAR(50), UNITS INTEGER, DISC VARCHAR(512), FOREIGN KEY(CID) REFERENCES module_category(CID))ENGINE=InnoDB DEFAULT CHARSET=utf8;
	INSERT INTO modules (CID, TITLE, UNITS, DISC) VALUES
		(1, "FAS", 1,"Self Assessment is a tool that helps you to understand more about yourself on fear of flying."),
		(2, "FACTSHEET",1,"The factsheet contains WhatIf scenarios about airplanes, with details as to what occurs in these scenarios."),
		(3, "EMDR", 1,  "EMDR is a kind of therapeutic tool that helps you to distract from the plane by focusing on a moving ball"),
		(3, "Audio: Introduction", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."), 
		(3,"Audio: Graduated Progressive Muscle Relaxation", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself." ),
		(3, "Audio: Passive Progressive Relaxation", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."),
		(3, "Audio: Rotation of Awareness", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."),
		(3, "Audio: Breath Awareness", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."),
		(3, "Audio: Word Focus Meditation", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."),
		(3, "Audio: Alternative Nostril Breathing Meditation", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."),
		(3, "Audio: Mindfull Awareness", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."),
		(3, "Audio: Mindfullness Meditation", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."),
		(3, "Audio: Breathing Techniques", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."),
		(3, "Audio: Quick Release Technique",1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself." ),
		(3, "Audio: Standing Relaxation", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself." ),
		(3, "Audio: Self-Hypnosis", 1,"Relaxation audio is kind of therapeutic tool that helps you to learn techniques to relax yourself."),
		(3, "Biofeedback", 1,"Biofeedback is kind of the therapeutic tool that helps you to understand whether you become more relax or anxious. At the beginning, some data will be collected from your body and then generate a baseline in BLUE. After that a RED real time monitoring line reflects your current feeling. If the red line goes up means you feel relax. If the red line goes down that means you feel anxious."),
		(4, "Systematic Desensitisation", 5,"Systematic Desensitisation is kind of therapy that allows you to go through some airline like scenarios. You have to apply techniques learned from the provided therapeutic tools and try to relax your self. There are 5 levels of scenarios provided in this version.");
            CREATE TABLE IF NOT EXISTS treatmentplan_category(TCID INTEGER PRIMARY KEY AUTO_INCREMENT, TITLE VARCHAR(30))ENGINE=InnoDB DEFAULT CHARSET=utf8;
            INSERT INTO treatmentplan_category (TITLE) VALUES("SHORT FLIGHT"), ("LONG FLIGHT"), ("MISC");
            CREATE TABLE IF NOT EXISTS status_type(SID INTEGER PRIMARY KEY AUTO_INCREMENT, TITLE VARCHAR(30))ENGINE=InnoDB DEFAULT CHARSET=utf8;
            INSERT INTO status_type (TITLE) VALUES("NOT STARTED"), ("IN PROGRESS"), ("COMPLETED"), ("DELETED");
            CREATE TABLE IF NOT EXISTS treatmentplan(TID INTEGER PRIMARY KEY AUTO_INCREMENT, 
				UID VARCHAR(60) NOT NULL, TCID INTEGER NOT NULL, date_added datetime, FOREIGN KEY(UID) REFERENCES users(UID), FOREIGN KEY(TCID) REFERENCES treatmentplan_category(TCID))ENGINE=InnoDB DEFAULT CHARSET=utf8;
            
            
            CREATE TABLE IF NOT EXISTS user_modules(TID INTEGER NOT NULL, INDX INTEGER NOT NULL, MID INTEGER, SID INTEGER, progress INTEGER, last_updated datetime, PRIMARY KEY(TID, INDX), FOREIGN KEY(TID) REFERENCES treatmentplan(TID), FOREIGN KEY(MID) REFERENCES modules(MID), FOREIGN KEY(SID) REFERENCES status_type(SID))ENGINE=InnoDB DEFAULT CHARSET=utf8;
            
            CREATE TABLE IF NOT EXISTS module_result(TID INTEGER NOT NULL, INDX INTEGER NOT NULL, 
				result VARCHAR(60), PRIMARY KEY(TID, INDX), FOREIGN KEY(TID, INDX) REFERENCES user_modules(TID, INDX))ENGINE=InnoDB DEFAULT CHARSET=utf8;
        
