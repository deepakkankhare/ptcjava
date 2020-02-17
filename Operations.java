import java.io.IOException;
import java.util.Iterator;

import com.mks.api.CmdRunner;
import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.Session;
import com.mks.api.response.APIException;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItem;
import com.mks.api.response.WorkItemIterator;
import com.mks.api.response.impl.FieldImpl;

public class Operations {

	Session mysession = null;
	CmdRunner mycmd = null;

	SessionManager session = new SessionManager();
	
	

	public String createproject() {
		// TODO Auto-generated method stub
		String projectname = null;
		String fields1 = null, fields2=null;
		try {
			
			mysession = session.Connect();
			mycmd = mysession.createCmdRunner();
			Command cmd1 = new Command(Command.SI, "createproject");
			cmd1.addSelection("/Eclipse_Project1/project.pj");
			Response resp = mycmd.execute(cmd1);
			WorkItemIterator wiiterator = resp.getWorkItems();
			while (wiiterator.hasNext()) {
				WorkItem wi = wiiterator.next();
				Iterator it = wi.getFields();
				while (it.hasNext()) {
					FieldImpl field = (FieldImpl) it.next();
					fields1 = field.getName().toString();
					fields2 = field.getValueAsString();
					System.out.println(fields1+":"+fields2 );
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception in si_createproject() !" + e);

		}
		return fields2;
	}

	public void createSandBox() {
		// TODO Auto-generated method stub
		String path = "\\HCLT-PC4391-1/LocalServerShare/PTC_Eclipse_Project_Sandboxs/Project1";
		String projectname = "/Eclipse_Project1/project.pj";

		try {
			mysession = session.Connect();
			mycmd = mysession.createCmdRunner();
			Command cmd1 = new Command(Command.SI, "createsandbox");
			cmd1.addOption(new Option("Project", projectname));
			// cmd1.addSelection(path);
			Response resp = mycmd.execute(cmd1);			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception in si_createsandbox() !" + e);
			System.err.println(e.getMessage());
		}
	}

	public void viewSandBoxes() {
		// TODO Auto-generated method stub
		try {
			mysession = session.Connect();
			mycmd = mysession.createCmdRunner();
			Command cmd1 = new Command(Command.SI, "sandboxes");
			Response resp = mycmd.execute(cmd1);
			System.out.println(resp.getResult());
			

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception in si_createsandbox() !"+e );
			e.getMessage();
		}

	}

	public void releaseSession() {
		try {
			mysession.release();
			System.out.println("Session closed !");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportSandbox(String cwd) {
		// TODO Auto-generated method stub
		try {
			mysession = session.Connect();
			mycmd = mysession.createCmdRunner();
			Command cmd1 = new Command(Command.SI, "exportsandbox");
			cmd1.addOption((new Option("cwd", cwd)));
			cmd1.addOption(new Option("exportFiles", "all"));
			cmd1.addOption((new Option("sandbox","remote://e:/PTC_Eclipse_Project_Sandboxs/project1/project.pj")));
			Response resp = mycmd.execute(cmd1);
			System.out.println(resp.getResult());
			System.out.println("Project.pjx is generated on" + cwd);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception in si_Exportsandbox() !" + e);
		}

	}

	public void importSandBox() {
		String targetSandBoxDir= "C:/Users/deepak.kankhare/Documents/PTCPRO";
		try {
			Command cmd1 = new Command(Command.SI, "importsandbox");
			cmd1.addOption((new Option("targetSandboxDir", targetSandBoxDir)));
			cmd1.addSelection("c:/Users/deepak.kankhare/Documents/SandBox/project.pjx");	
			
			Response resp = mycmd.execute(cmd1);
			System.out.println(resp.getResult());
			System.out.println("Project.pjx is generated on" +targetSandBoxDir);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception in si_Exportsandbox() !" + e);
		}

		
		
	}

}
