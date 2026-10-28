import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PatientList patients = new PatientList();

        File patientFile = new File("patients1000.csv");
        File prescriptionFile = new File("prescriptions1000.csv");

        System.out.println("Working directory: " +
                System.getProperty("user.dir"));

        System.out.println("Patient file exists: " +
                patientFile.exists());

        System.out.println("Prescription file exists: " +
                prescriptionFile.exists());

        System.out.println();
        System.out.println("Loading patient records...");

        boolean patientsImported =
                patients.importFromFile("patients1000.csv");

        if (patientsImported) {
            System.out.println("Patients imported successfully.");
        } else {
            System.out.println("Patient import encountered errors.");
        }

        System.out.println();
        System.out.println("Loading prescription records...");

        boolean prescriptionsImported =
                patients.importPrescriptionsFromFile(
                        "prescriptions1000.csv");

        if (prescriptionsImported) {
            System.out.println("Prescriptions imported successfully.");
        } else {
            System.out.println("Prescription import encountered errors.");
        }

        BinarySearchTree<Patient> patientTree =
                new BinarySearchTree<>();

        PatientList.Iterator iterator = patients.iterator();
        Patient patient;

        while ((patient = iterator.next()) != null) {
            patientTree.add(patient);
        }

        System.out.println();
        System.out.println("Total Patients: " +
                patients.getPatientCount());

        System.out.println("Patients in Binary Search Tree: " +
                patientTree.size());

        if (patientTree.isEmpty()) {
            System.out.println("No patient records available.");
            scanner.close();
            return;
        }

        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd");

        format.setLenient(false);

        boolean searching = true;

        while (searching) {
            System.out.println();
            System.out.print("Enter patient's first name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Enter patient's last name: ");
            String lastName = scanner.nextLine().trim();

            if (firstName.isEmpty() || lastName.isEmpty()) {
                System.out.println(
                        "Both first and last names are required.");
            } else {
                Date birthDate = null;

                while (birthDate == null) {
                    System.out.print(
                            "Enter Date of Birth (yyyy-MM-dd): ");

                    String birthDateText =
                            scanner.nextLine().trim();

                    try {
                        Date parsedDate =
                                format.parse(birthDateText);

                        if (format.format(parsedDate)
                                .equals(birthDateText)) {
                            birthDate = parsedDate;
                        } else {
                            System.out.println(
                                    "Invalid date. Use yyyy-MM-dd.");
                        }
                    } catch (ParseException e) {
                        System.out.println(
                                "Invalid date. Use yyyy-MM-dd.");
                    }
                }

                Identity identity =
                        new Identity(firstName, lastName);

                PatientIdentity patientIdentity =
                        new PatientIdentity(identity, birthDate);

                Patient searchPatient =
                        new Patient(patientIdentity);

                System.out.println();
                System.out.println("Searching for: " +
                        firstName + " " + lastName);

                System.out.println("Date of Birth: " +
                        format.format(birthDate));

                Patient foundPatient =
                        patientTree.find(searchPatient);

                if (foundPatient == null) {
                    System.out.println();
                    System.out.println("Patient not found.");
                } else {
                    Identity foundName =
                            foundPatient.getIdentity().getIdentity();

                    System.out.println();
                    System.out.println("Patient Record:");

                    System.out.println("Name: " +
                            foundName.getFirstName() + " " +
                            foundName.getLastName());

                    System.out.println("Date of Birth: " +
                            format.format(
                                    foundPatient.getIdentity()
                                            .getDateOfBirth()));

                    System.out.println("Patient ID: " +
                            foundPatient.getPatientID());

                    System.out.println();
                    System.out.println("Prescription History:");

                    foundPatient.printPrescriptionHistory();
                }
            }

            System.out.println();
            System.out.print(
                    "Search for another patient? (Yes/No): ");

            String answer = scanner.nextLine().trim();

            if (!answer.equalsIgnoreCase("Yes") &&
                    !answer.equalsIgnoreCase("Y")) {
                searching = false;
            }
        }

        scanner.close();
        System.out.println("Program finished.");
    }
}
