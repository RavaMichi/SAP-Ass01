package ass01.plugins;

import ass01.core.business.entities.User;
import ass01.core.business.services.PluginParameter;
import ass01.core.business.services.RentalServicePlugin;
import ass01.core.business.services.RentalServiceState;

public class AddUserPlugin implements RentalServicePlugin {
    @Override
    public RentalServiceState apply(RentalServiceState previousState, PluginParameter parameters) {
        User user = new User(parameters.userId());
        user.rechargeCredit(100);
        previousState.users().add(user);
        return previousState;
    }

    @Override
    public String operationName() {
        return "Add User";
    }
}
