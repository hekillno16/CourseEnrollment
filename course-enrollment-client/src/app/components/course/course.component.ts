import { Component, OnInit, ViewChild } from '@angular/core';
import { LogService } from 'src/app/services/log.service';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { CourseService } from 'src/app/services/course.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Course } from 'src/app/model/Course';
import { Log } from 'src/app/model/Log';
import { Ip } from 'src/app/model/Ip';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  courseId: string;
  currentCourse: Course;
  currentLog: Log;
  courseHitCount: any;

  displayedColumns: string[] = ['name'];
  dataSource: MatTableDataSource<string> = new MatTableDataSource();
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  constructor(
    private logService: LogService,
    private courseService: CourseService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.currentCourse = JSON.parse(localStorage.getItem('currentCourse'));
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      if (params.has('id')) {
        this.courseId = params.get('id');

        this.currentLog = new Log();
        this.currentLog.courseId = this.courseId;
        
        this.createLog();
        this.showSummary();
        this.findStudents();
      }
    });
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  createLog() {
    this.logService.getIpClient().subscribe(
      (data: Ip) => {
        this.currentLog.ip = data.ip;
        this.hit(data.ip);
      }
    );
  }

  hit(ip) {
    this.logService.createLog(this.currentLog)
      .subscribe(
        data => {
          console.log("hit: " + ip);
        }
      );
  }

  showSummary() {
    this.logService.getSummaryOfCourse(this.courseId)
      .subscribe(
        data => {
          if (data) {
            this.courseHitCount = data.hitCount;
          } else {
            this.courseHitCount = 0;
          }
        }
      );
  }

  findStudents() {
    this.courseService.filterStudents(this.courseId)
      .subscribe(
        data => {
          this.dataSource.data = data;
        }
      );
  }
}
