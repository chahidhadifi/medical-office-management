package utils;

public class IdGenerator {
    
    public static int patientId = 0;
    public static int adminId = 0;
    public static int doctorId = 0;
    
    public static int getPatientId() {
        return ++patientId;
    }
    
    public static int getAdminId() {
        return ++adminId;
    }
    
    public static int getDoctorId() {
        return ++doctorId;
    }
    
}
