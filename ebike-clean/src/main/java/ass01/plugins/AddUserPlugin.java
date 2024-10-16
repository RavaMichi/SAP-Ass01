package ass01.plugins;

import ass01.core.domain.entities.User;
import ass01.core.domain.services.*;
import ass01.core.domain.ports.*;

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
