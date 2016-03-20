package rhp.aof4oop.cs.apps;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import rhp.aof4oop.cs.datamodel.Coordinator;
import rhp.aof4oop.cs.datamodel.ExamOfficer;
import rhp.aof4oop.cs.datamodel.Moderator;
import rhp.aof4oop.cs.datamodel.Principal;
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
		ObjectContainer db=Utils.openDB();
		try 
		{
			// check previously created database
			System.out.println("Before");
			Utils.showDB(db);
			System.out.println();
			
			moveStudentsFromTemporaryClassToVersionB(db);
			moveStaffFromTemporaryClassToVersionB(db);
			
			// Show database after data migration
			System.out.println("After");
			Utils.showDB(db);

		}
		finally 
		{
		    db.close();
		}
	}
	/**
	 * Move Student objects from the temporary to the definitive one
	 * @param db
	 */
	private static void moveStudentsFromTemporaryClassToVersionB(ObjectContainer db)
	{
		//Students
		ObjectSet<rhp.aof4oop.cs.datamodel_b.Student> result_students_a = db.queryByExample(rhp.aof4oop.cs.datamodel_b.Student.class);
		System.out.println("Moving Students from temporary class to version B: "+result_students_a.size());
		for(rhp.aof4oop.cs.datamodel_b.Student s:result_students_a)
		{
			Student s_b=new Student(s.getFirstName(),s.getMiddleName(),s.getLastName(),s.getBirth(),s.getSex());
			db.store(s_b);
			db.delete(s);
		}
	}
	/**
	 * Move Staff objects from the temporary to the definitive one
	 * @param db
	 * @throws Exception 
	 */
	private static void moveStaffFromTemporaryClassToVersionB(ObjectContainer db) throws Exception
	{
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
	}
}
