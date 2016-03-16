package rhp.aof4oop.cs.apps;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;

import rhp.aof4oop.cs.datamodel.Coordinator;
import rhp.aof4oop.cs.datamodel.ExamOfficer;
import rhp.aof4oop.cs.datamodel.Moderator;
import rhp.aof4oop.cs.datamodel.Person;
import rhp.aof4oop.cs.datamodel.Principal;
import rhp.aof4oop.cs.datamodel.Staff;
import rhp.aof4oop.cs.datamodel.Student;
import rhp.aof4oop.cs.datamodel.Tutor;


/**
 * This application moves objects from a temporary classes with a structure in version B to definitive classes in version B
 * @author rhp
 */
public class MigrateDBStep2 
{
	public static void main(String[] args) throws Exception
	{
		//Dropping earlier class definitions in the database configuration
		EmbeddedConfiguration cfg = Db4oEmbedded.newConfiguration();
		cfg.common().objectClass(Student.class).rename("rhp.trash.Student");
		cfg.common().objectClass(Person.class).rename("rhp.trash.Person");
		cfg.common().objectClass(Staff.class).rename("rhp.trash.Staff");
		cfg.common().objectClass(Principal.class).rename("rhp.trash.Principal");
		cfg.common().objectClass(Coordinator.class).rename("rhp.trash.Coordinator");
		cfg.common().objectClass(ExamOfficer.class).rename("rhp.trash.ExamOfficer");
		cfg.common().objectClass(Tutor.class).rename("rhp.trash.Tutor");
		cfg.common().objectClass(Moderator.class).rename("rhp.trash.Moderator");
		
		ObjectContainer db=Db4oEmbedded.openFile(cfg,Utils.DB4OFILENAME);
		try 
		{
			// check previously created database
			System.out.println("Before");
			Utils.showDB(db);
			System.out.println();
			
			//Students
			ObjectSet<rhp.aof4oop.cs.datamodel_b.Student> result_students_a = db.queryByExample(rhp.aof4oop.cs.datamodel_b.Student.class);
			System.out.println("Moving Students from temporary class to version B: "+result_students_a.size());
			for(rhp.aof4oop.cs.datamodel_b.Student s:result_students_a)
			{
				Student s_b=new Student(s.getFirstName(),s.getMiddleName(),s.getLastName(),s.getBirth(),s.getSex());
				db.store(s_b);
				db.delete(s);
			}
			//Staff
			ObjectSet<rhp.aof4oop.cs.datamodel_b.Staff> result_staff_a = db.queryByExample(rhp.aof4oop.cs.datamodel_b.Staff.class);
			System.out.println("Moving Staff from temporary class to version B: "+result_staff_a.size());
			for(rhp.aof4oop.cs.datamodel_b.Staff p:result_staff_a)
			{
				if(p instanceof rhp.aof4oop.cs.datamodel_b.Principal)
				{
					Principal p_b=new Principal(p.getTitle(),p.getFirstName(),p.getMiddleName(),p.getLastName(),p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
					db.store(p_b);
				}
				else if(p instanceof rhp.aof4oop.cs.datamodel_b.Coordinator)
				{
					Coordinator p_b=new Coordinator(p.getTitle(),p.getFirstName(),p.getMiddleName(),p.getLastName(),p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
					db.store(p_b);
				}
				else if(p instanceof rhp.aof4oop.cs.datamodel_b.ExamOfficer)
				{
					ExamOfficer p_b=new ExamOfficer(p.getTitle(),p.getFirstName(),p.getMiddleName(),p.getLastName(),p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
					db.store(p_b);
				}
				else if(p instanceof rhp.aof4oop.cs.datamodel_b.Tutor)
				{
					Tutor p_b=new Tutor(p.getTitle(),p.getFirstName(),p.getMiddleName(),p.getLastName(),p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
					db.store(p_b);
				}
				else if(p instanceof rhp.aof4oop.cs.datamodel_b.Moderator)
				{
					Moderator p_b=new Moderator(p.getTitle(),p.getFirstName(),p.getMiddleName(),p.getLastName(),p.getAddress(),p.getPostCode(),p.getTelephoneNumber(),p.getFaxNumber(),p.getMobileNumber(),p.isPassedD32Qualification(),p.isPassedD34Qualification(),p.isPassedD36Qualification());
					db.store(p_b);
				}
				else
				{
					throw new Exception("Unexpected object type!!!");
				}
				db.delete(p);
			}
			// Show database after data migration
			System.out.println("After");
			Utils.showDB(db);

		}
		finally 
		{
		    db.close();
		}
	}
}
