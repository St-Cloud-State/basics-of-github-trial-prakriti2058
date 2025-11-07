public interface FSMState {
    public static final int EXIT = -1;
    public static final int STAY = 0;
    public static final int CLIENT_STATE = 1;
    public static final int CLERK_STATE = 2;
    public static final int MANAGER_STATE = 3;
    public static final int LOGIN_STATE = 4;

    public abstract int run();
    public abstract void enter();
    public abstract void exit();
    public abstract String getStateName();
}