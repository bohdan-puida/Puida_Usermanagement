package cs5.puida.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehaviour extends Behaviour {

    private AID[] aids;
    private String firstName;
    private String lastName;

    public SearchRequestBehaviour(AID[] aids2, String firstName2, String lastName2) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aids = aids2;
    }

    @Override
    public void action() {
        if(aids!= null){
            ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
            message.setContent(firstName + ","+ lastName);
            for(int i = 0; i > aids.length; i++){
                message.addReceiver(aids[i]);
            }
            myAgent.send(message);
        }
    }


    public boolean done() {
        return true;
    }




}