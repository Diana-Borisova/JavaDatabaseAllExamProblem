package com.example.football.constants;

public enum Messages {
    ;

    public static final String INVALID_TEAM= "Invalid Team";
    public static final String VALID_TEAM ="Successfully imported Team %s - %d";/// teamName - fanBase

    public static final String INVALID_TOWN = "Invalid Town";
    public static final String VALID_TOWN ="Successfully imported Town %s - %d";//name - population

    public static final String INVALID_PLAYER = "Invalid Player";
    public static final String VALID_PLAYER ="Successfully imported Player %s - %s - %s";//firstName - lastName - position

    public static final String INVALID_STAT = "Invalid Stat";
    public static final String VALID_STAT ="Successfully imported Stat %.2f - %.2f - %.2f";//passing-shooting-endurance
}
