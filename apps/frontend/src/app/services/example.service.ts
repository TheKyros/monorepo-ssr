import { inject, Injectable, REQUEST } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Example, CreateExampleRequest } from '../models/example.model';

@Injectable({
  providedIn: 'root',
})
export class ExampleService {
  private apiUrl = 'http://localhost:8080/api/examples';
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
    withCredentials: true,
  };

  constructor(private http: HttpClient) {
    const request = inject(REQUEST);
    console.log(request);
  }

  getAll(): Observable<Example[]> {
    return this.http.get<Example[]>(this.apiUrl, this.httpOptions);
  }

  getById(id: number): Observable<Example> {
    return this.http.get<Example>(`${this.apiUrl}/${id}`, this.httpOptions);
  }

  create(example: CreateExampleRequest): Observable<Example> {
    return this.http.post<Example>(this.apiUrl, example, this.httpOptions);
  }

  update(id: number, example: Example): Observable<Example> {
    return this.http.put<Example>(
      `${this.apiUrl}/${id}`,
      example,
      this.httpOptions
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, this.httpOptions);
  }
}
