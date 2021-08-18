import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  constructor(private http: HttpClient) { }
  download(type: string){
    let params = new HttpParams().set('type', type);
    return this.http.get("http://localhost:8080/rest-consumer/api/v1/post/download", {responseType: 'blob', params:params});
  }
}
