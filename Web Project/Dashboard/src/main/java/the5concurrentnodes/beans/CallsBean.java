package the5concurrentnodes.beans;

import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Sms;
import the5concurrentnodes.managers.CallManager;
import the5concurrentnodes.managers.SmsManager;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CallsBean {

    @Inject
    CallManager callManager;

    public List<Call> getCallLogs() {

        return callManager.getAllCallLogs();
    }
}
