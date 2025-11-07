public class WarehouseFSM {
    private static ClientData context;
    private static FSMState currentState;

    public WarehouseFSM() {
        context = new ClientData();
        currentState = null;
    }

    public void run() {
        // Start at login state
        changeState(FSMState.LOGIN_STATE);

        // Run until exit
        while (currentState != null) {
            int nextState = currentState.run();
            if (nextState == FSMState.EXIT) {
                break;
            }
            changeState(nextState);
        }
    }

    private void changeState(int stateId) {
        // Exit current state
        if (currentState != null) {
            currentState.exit();
        }

        // Set new state
        switch (stateId) {
            case FSMState.CLIENT_STATE:
                currentState = ClientMenuState.instance();
                ClientMenuState.setContext(context);
                break;
            case FSMState.CLERK_STATE:
                currentState = ClerkMenuState.instance();
                ClerkMenuState.setContext(context);
                break;
            case FSMState.MANAGER_STATE:
                System.out.println("Manager state not yet implemented.");
                currentState = null;
                break;
            case FSMState.LOGIN_STATE:
                currentState = LoginState.instance();
                LoginState.setContext(context);
                break;
            default:
                currentState = null;
                return;
        }

        // Enter new state
        if (currentState != null) {
            currentState.enter();
        }
    }

    public static void main(String[] args) {
        WarehouseFSM fsm = new WarehouseFSM();
        fsm.run();
    }
}