import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';



import { Observable } from 'rxjs';
import { baseUrl } from './helper';

@Injectable({
  providedIn: 'root'
})
export class DoctorAppointmentsService {
  private baseUrl = 'http://localhost:8083/appointment';
  private patientUrl = 'http://localhost:8083/patient';
  private feedbackUrl = 'http://localhost:8083/patient';
  private searchUrl = 'http://localhost:8083/search';
 
  constructor(private http: HttpClient) { }

  getAppointmentsOfDoctor(doctorId: any): Observable<any> {
    const requestBody = {
      doctorId: doctorId,
      appointmentStatus: 'PENDING'
    };
    return this.http.post<any>(`${this.searchUrl}/searchAppointments`, requestBody);
  }
  getReviewsOfDoctor(doctorId: any, page: any, size: any): Observable<any> {
    const params = new HttpParams().set('id', doctorId).set('page', page).set('size', size);
    // Make the GET request with parameters
    return this.http.get<any>(`${this.feedbackUrl}/get-feedback`, { params });
  }

  getReviewsOfDoctor1(doctorId: any): Observable<any> {
    return this.http.get<any>(`${this.feedbackUrl}/get-feedback-once?id=${doctorId}`);
  }
  getReviews(doctorId: any): Observable<any> {
    const params = new HttpParams().set('id', doctorId);
    // Make the GET request with parameters
    return this.http.get<any>(`${this.searchUrl}/get-feedback`, { params });
  }

  getParticularDoctor(doctorId: any): Observable<any> {

    // Make the GET request with parameters
    // return this.http.get<any>(`/api/users/${userId}`);
    console.log("INSDIE SERVICE")
    return this.http.get<any>(`${this.patientUrl}/get-particular-doctor/${doctorId}`);
  }

  bookDoctorAppointment(doctorId: any, patientId: any, appDate: any, slot: any): Observable<any> {
    const requestBody = {
      doctorId: doctorId,
      patientId: patientId,
      appDate: appDate,
      slot: slot
    };

    return this.http.post<any>(`${this.baseUrl}/book`, requestBody);
  }

  getUpcomingAppointments(doctorId: any): Observable<any[]> {
    return this.http.post<any>(`${this.searchUrl}/searchAppointments`, { doctorId });
  }

}

/* getCompletedAppointments(): any[] {
   return [
     { date: '22/10/2024', time: '12:00 PM', description: 'Completed Appointment 1', patient: 'Muskan Sharma' },
     { date: '22/10/2024', time: '12:00 PM', description: 'Completed Appointment 2', patient: 'Aishwarya Gupta' },
     { date: '22/10/2024', time: '12:00 PM', description: 'Completed Appointment 3', patient: 'Joe Roy' },
     { date: '22/10/2024', time: '12:00 PM', description: 'Completed Appointment 4', patient: 'Akanksha Dwivedi' },
     { date: '22/10/2024', time: '12:00 PM', description: 'Upcoming Appointment 1', patient: 'Jane Smith' },
     { date: '22/10/2024', time: '12:20 PM', description: 'Upcoming Appointment 2', patient: 'Sakshi Saha' },
     { date: '22/10/2024', time: '12:20 PM', description: 'Upcoming Appointment 3', patient: 'Giya Roy' }
   ];
 } */


