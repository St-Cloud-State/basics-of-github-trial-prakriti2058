/**
 * ClientData class - Context for FSM operations
 * Stores session information for client state transitions
 */
public class ClientData {
    private String clientId;           // Current active client ID
    private FSMState previousState;    // Previous state for navigation on logout
    private boolean isClerkLogin;      // Flag indicating if accessed from clerk state

    public ClientData() {
        this.clientId = null;
        this.previousState = null;
        this.isClerkLogin = false;
    }

    /**
     * Gets the current client ID
     * @return client ID or null if no client selected
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the current client ID
     * @param clientId the ID to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the previous state (for logout navigation)
     * @return previous FSM state
     */
    public FSMState getPreviousState() {
        return previousState;
    }

    /**
     * Sets the previous state (for logout navigation)
     * @param state the state to return to
     */
    public void setPreviousState(FSMState state) {
        this.previousState = state;
    }

    /**
     * Checks if current session started from clerk mode
     * @return true if from clerk, false if direct login
     */
    public void setClerkLogin(boolean isClerkLogin) {
        this.isClerkLogin = isClerkLogin;
    }

    public boolean isClerkLogin() {
        return isClerkLogin;
    }

    /**
     * Clears all session data for logout
     */
    public void clear() {
        clientId = null;
        previousState = null;
        isClerkLogin = false;
    }

    /**
     * String representation of context
     */
    @Override
    public String toString() {
        return "ClientData [clientId=" + clientId + ", isClerkLogin=" + isClerkLogin + "]";
    }
}