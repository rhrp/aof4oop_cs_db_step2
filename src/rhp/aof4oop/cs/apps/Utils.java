package rhp.aof4oop.cs.apps;

import java.util.Enumeration;
import java.util.Hashtable;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import rhp.aof4oop.cs.datamodel.Coordinator;
import rhp.aof4oop.cs.datamodel.ExamOfficer;
import rhp.aof4oop.cs.datamodel.Moderator;
import rhp.aof4oop.cs.datamodel.Person;
import rhp.aof4oop.cs.datamodel.Principal;
import rhp.aof4oop.cs.datamodel.Student;
import rhp.aof4oop.cs.datamodel.Tutor;

/**
 * 
 * @author rhp
 *
 */
public class Utils 
{
	public static String DB4OFILENAME	= "database_a.dbf";

	public static ObjectContainer openDB()
	{
		// accessDb4o
		return Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB4OFILENAME);
	}
	
	public static boolean populatedDB(ObjectContainer db)
	{
		ObjectSet<Student> result_students = db.queryByExample(Student.class);
		return (result_students.size()>0);
	}
	
	public static void showDB(ObjectContainer db)
	{
		Hashtable<String,Integer> total=new Hashtable<String,Integer>();
		ObjectSet<Object> result_objects = db.queryByExample(Object.class);
		System.out.println("Objects: "+result_objects.size());
		for(Object o:result_objects)
		{
			Integer t=total.get(o.getClass().getName());
			if(t==null)
			{
				total.put(o.getClass().getName(),1);
			}
			else
			{
				total.put(o.getClass().getName(),t.intValue()+1);
			}
		}
		Enumeration<String> keys = total.keys();
		while(keys.hasMoreElements())
		{
			String k = keys.nextElement();
			System.out.println(k+"="+total.get(k));
		}
		
		ObjectSet<Person> result_students = db.queryByExample(Student.class);
		System.out.println("Sudents: "+result_students.size());
		showPerson(result_students);
		
		ObjectSet<Person> result_principal = db.queryByExample(Principal.class);
		System.out.println("Principal: "+result_principal.size());
		showPerson(result_principal);
		
		ObjectSet<Person> result_coordinator = db.queryByExample(Coordinator.class);
		System.out.println("Coordinator: "+result_coordinator.size());
		showPerson(result_coordinator);
		
		ObjectSet<Person> result_exam_officer = db.queryByExample(ExamOfficer.class);
		System.out.println("ExamOfficer: "+result_exam_officer.size());
		showPerson(result_exam_officer);
		
		ObjectSet<Person> result_tutor = db.queryByExample(Tutor.class);
		System.out.println("Tutor: "+result_tutor.size());
		showPerson(result_tutor);
		
		ObjectSet<Person> result_moderator = db.queryByExample(Moderator.class);
		System.out.println("Moderator: "+result_moderator.size());
		showPerson(result_moderator);
	}
	
	
	private static void showPerson(ObjectSet<Person> result)
	{
		int n=1;
		Object sample=null;
		while(n<5 && result.size()>n)
		{
			Person p = result.get(n);
			System.out.println("first: "+p.getFirstName()+"   middle: "+p.getMiddleName()+" last: "+p.getLastName());
			n++;
			sample=p;
		}
		if(result.size()>n && sample!=null)
		{
			System.out.println("More "+(result.size()-n)+" "+sample.getClass().getSimpleName()+"...");
		}
	}
}
