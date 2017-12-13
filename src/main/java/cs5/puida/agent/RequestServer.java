package cs5.puida.agent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

import cs5.puida.User;
import cs5.puida.db.DaoFactory;
import cs5.puida.db.DatabaseException;
import org.hsqldb.lib.Iterator;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class RequestServer extends CyclicBehaviour {

    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if(message != null){
            if(message.getPerformative() == ACLMessage.REQUEST){
                myAgent.send(createReply(message));
            }
            else{
                Collection users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        }
        else{
            block();
        }
    }



    private ACLMessage createReply(ACLMessage message) {
        ACLMessage reply = message.createReply();
        String content = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(content, ",");
        if(tokenizer.countTokens() == 2){
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            Collection users = null;
            try{
                users = DaoFactory.getInstance().getUserDao().find(firstName,lastName);
            }
            catch(DatabaseException e){
                e.printStackTrace();
                users = new ArrayList(0);
            }
            StringBuffer buffer = new StringBuffer();
            for(java.util.Iterator it = users.iterator();it.hasNext();){
                User user = (User) it.next();
                buffer.append(user.getId()).append(",");
                buffer.append(user.getFirstName()).append(",");

                buffer.append(user.getLastName()).append(";");

            }
            reply.setContent(buffer.toString());
        }
        return reply;
    }

    private Collection parseMessage(ACLMessage message) {
        Collection users = new LinkedList();
        String content = message.getContent();
        if(content != null){
            StringTokenizer tokenizer1 = new StringTokenizer (content, ";");
            while(tokenizer1.hasMoreTokens()){
                String userInfo = tokenizer1.nextToken();
                StringTokenizer tokenizer2 = new StringTokenizer (userInfo, ",");
                String id = tokenizer2.nextToken();
                String firstName = tokenizer2.nextToken();
                String lastName = tokenizer2.nextToken();
                users.add(new User(new Long(id), firstName, lastName, null));

            }
        }
        return users;
    }

}
