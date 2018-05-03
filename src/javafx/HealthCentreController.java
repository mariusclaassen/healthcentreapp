package javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class HealthCentreController implements Initializable {

    private static final double Y_CONTRIBUTION_RATE = 70;
    private static final double C_CONTRIBUTION_RATE = Y_CONTRIBUTION_RATE * 1.1;
    private static final double L_CONTRIBUTION_RATE = C_CONTRIBUTION_RATE * 1.1;
    private static final double M_CONTRIBUTION_RATE = L_CONTRIBUTION_RATE * 1.1;

    private int paymentNo;
    private ToggleGroup toggleGroup = new ToggleGroup();

    // Health care plans
    @FXML private ListView<String> listViewHealthPlans;
    @FXML private TextField textFieldDependants;
    @FXML private Button buttonHealthPlans;

    // Payments
    @FXML private TextField textFieldBalance;
    @FXML private TableView<Payment> tableViewPmtSchedule;
    @FXML private TableColumn<Payment, Integer> tableColumnNumber;
    @FXML private TableColumn<Payment, Integer> tableColumnPayment;
    @FXML private TableColumn<Payment, Integer> tableColumnBalance;
    @FXML private Button buttonPayment;

    // Doctor
    @FXML private TextField textFieldNumberOfPatients;
    @FXML private Label labelDoctorTsade;
    @FXML private Label labelDoctorKuf;
    @FXML private Label labelDoctorReish;
    @FXML private Button buttonDoctor;

    @FXML private TextField textFieldMinutesPerPatient;
    @FXML private RadioButton radioButtonEight;
    @FXML private RadioButton radioButtonEleven;
    @FXML private RadioButton radioButtonThirteen;
    @FXML private Label labelTimeAtWork;
    @FXML private Label labelSignOutTime;

    @Override public void initialize(URL location, ResourceBundle rb) {

        textFieldDependants.setText("");
        listViewHealthPlans.setItems(FXCollections.observableArrayList(
                "Option Yud", "Option Chaf", "Option Lamed", "Option Mem"
        ));
        textFieldBalance.setText("");
        textFieldNumberOfPatients.setText("");
        textFieldMinutesPerPatient.setText("");
    }

    @FXML private void healthPlanButtonClick() {

        buttonHealthPlans.setOnAction(e -> {
            if (validHealthPlan()) {

                String plan = listViewHealthPlans.getSelectionModel().getSelectedItem();
                int dependants = Integer.parseInt(textFieldDependants.getText());
                double contribution;

                switch (plan) {
                    case "Option Yud":  contribution =
                        Y_CONTRIBUTION_RATE + (dependants * 0.5 * Y_CONTRIBUTION_RATE);
                        break;
                    case "Option Chaf": contribution =
                        C_CONTRIBUTION_RATE + (dependants * 0.5 * C_CONTRIBUTION_RATE);
                        break;
                    case "Option Lamed":    contribution =
                        L_CONTRIBUTION_RATE + (dependants * 0.5 * L_CONTRIBUTION_RATE);
                        break;
                    default:    contribution =
                        M_CONTRIBUTION_RATE + (dependants * 0.5 * M_CONTRIBUTION_RATE);
                        break;
                }

                String result = String.format("%.2f", contribution);

                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Monthly payment: $" + result);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.show();
            }
        });
    }

    @FXML private void paymentButtonClick() {

        int totalOwed = Integer.parseInt(textFieldBalance.getText());
        int payment1 = Math.incrementExact((int) (totalOwed *.15));
        int balance1 = Math.decrementExact(totalOwed - payment1);
        int amtPerMonth = Math.round(balance1 / 11);
        int balance2 = Math.round(balance1 - amtPerMonth);

        tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("paymentNumber"));
        tableColumnPayment.setCellValueFactory(new PropertyValueFactory<>("monthlyPayment"));
        tableColumnBalance.setCellValueFactory(new PropertyValueFactory<>("amountOwed"));

        buttonPayment.setOnAction(e -> {
            paymentNo = 0;
             final ObservableList<Payment> data = FXCollections.observableArrayList(
                new Payment(++paymentNo, payment1, balance1),
                new Payment(++paymentNo, amtPerMonth, balance2),
                new Payment(++paymentNo, amtPerMonth, balance2 - amtPerMonth),
                new Payment(++paymentNo, amtPerMonth, balance2 - (amtPerMonth*2)),
                new Payment(++paymentNo, amtPerMonth, balance2 - (amtPerMonth*3)),
                new Payment(++paymentNo, amtPerMonth, balance2 - (amtPerMonth*4)),
                new Payment(++paymentNo, amtPerMonth, balance2 - (amtPerMonth*5)),
                new Payment(++paymentNo, amtPerMonth, balance2 - (amtPerMonth*6)),
                new Payment(++paymentNo, amtPerMonth, balance2 - (amtPerMonth*7)),
                new Payment(++paymentNo, amtPerMonth, balance2 - (amtPerMonth*8)),
                new Payment(++paymentNo, amtPerMonth, balance2 - (amtPerMonth*9)),
                new Payment(++paymentNo, amtPerMonth, balance2 - (amtPerMonth*10))
            );
            tableViewPmtSchedule.setItems(data);
        });
    }

    @FXML private void doctorButtonClick() {

        int totalPatients = Integer.parseInt(textFieldNumberOfPatients.getText());
        int patientsPerDoctor = totalPatients / 3;
        int remainder = totalPatients % 3;
        int doctorTsade = patientsPerDoctor;
        int doctorKuf = patientsPerDoctor;

        if (remainder == 2) {
            doctorTsade++;
            doctorKuf++;
        } else {
            doctorTsade++;
        }

        int hours = 0;
        int minutes;
        int aveConsultationTime = Integer.parseInt(textFieldMinutesPerPatient.getText());
        int totalConsultationTime = doctorTsade * aveConsultationTime;
        int hoursWorked = 0;

        if (totalConsultationTime < 60) {
            minutes = totalConsultationTime;
            hoursWorked++;
        } else if (totalConsultationTime < 120) {
            hours++;
            minutes = totalConsultationTime - 60;
            hoursWorked += 2;
        } else if (totalConsultationTime < 180) {
            hours += 2;
            minutes = totalConsultationTime - 60;
            hoursWorked += 3;
        } else if (totalConsultationTime < 240) {
            hours += 3;
            minutes = totalConsultationTime - 60;
            hoursWorked += 4;
        } else {
            hours += 4;
            minutes = totalConsultationTime - 60;
            hoursWorked += 5;
        }

        radioButtonEight.setToggleGroup(toggleGroup);
        radioButtonEleven.setToggleGroup(toggleGroup);
        radioButtonThirteen.setToggleGroup(toggleGroup);

        LocalTime time1 = LocalTime.of(8,0);
        LocalTime time2 = LocalTime.of(11,0);
        LocalTime time3 = LocalTime.of(13,0);
        final LocalTime signOut;

        if (toggleGroup.getSelectedToggle().equals(radioButtonEight)) {
            signOut = time1.plusHours(hoursWorked);
        } else if (toggleGroup.getSelectedToggle().equals(radioButtonEleven)) {
            signOut = time2.plusHours(hoursWorked);
        } else {
            signOut = time3.plusHours(hoursWorked);
        }

        String patientsDrTsade = String.format(" %d", doctorTsade);
        String patientsDrKuf = String.format(" %d", doctorKuf);
        String patientsDrReish = String.format(" %d", patientsPerDoctor);

        String timeAtWork = " "
                .concat(String.format("%d", hours))
                .concat(" hour(s)")
                .concat(String.format(" %d", minutes))
                .concat(" minutes");

        buttonDoctor.setOnAction(e -> {
                    labelDoctorTsade.setText(patientsDrTsade);
                    labelDoctorKuf.setText(patientsDrKuf);
                    labelDoctorReish.setText(patientsDrReish);

                    labelTimeAtWork.setText(timeAtWork);
                    labelSignOutTime.setText(signOut.toString());
                }
        );
    }

    private boolean validHealthPlan() {
        if (textFieldDependants.getText().isEmpty() ||
                (listViewHealthPlans.getSelectionModel().getSelectedItem().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Select health plan and enter number of dependants");
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
