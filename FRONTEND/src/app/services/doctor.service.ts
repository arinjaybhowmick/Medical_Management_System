import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doctor } from '../models/doctor.model';
import { baseUrl } from './helper';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private httpClient: HttpClient) { }

  api = "http://localhost:8083"

  public saveDoctor(doctor: Doctor): Observable<Doctor> {
    return this.httpClient.post<Doctor>(`${this.api}/admin/add-doctor`, doctor);
  }

  // public getEmployees(): Observable<Employee[]> {
  //     return this.httpClient.get<Employee[]>(`${this.api}/get/employee`);
  // }

  // public deleteEmployee(employeeId: number) {
  //   return this.httpClient.delete(`${this.api}/delete/employee/${employeeId}`);
  // }

  // public getEmployee(employeeId: number) {
  //   return this.httpClient.get<Employee>(`${this.api}/get/employee/${employeeId}`);
  // }

  // public updateEmployee(employee: Employee) {
  //   return this.httpClient.put<Employee>(`${this.api}/update/employee`, employee);
  // }

  public getFeedback(id : number){
    return this.httpClient.get(`${baseUrl}/search/feedback?doctorId=${id}`);
  }

}