package application.holder;

import application.models.Consultation;

public final class ConsultationHolder {
  
  private Consultation consultation;
  private final static ConsultationHolder INSTANCE = new ConsultationHolder();
  
  private ConsultationHolder() {}
  
  public static ConsultationHolder getInstance() {
    return INSTANCE;
  }
  
  public void setConsultation(Consultation consultation) {
    this.consultation = consultation;
  }
  
  public Consultation getConsultation() {
    return this.consultation;
  }
}