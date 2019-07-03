import { Component, OnInit, ViewChild } from '@angular/core';
import { CourseService } from 'src/app/services/course.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Course } from 'src/app/model/Course';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { Transaction } from 'src/app/model/Transaction';
import { EmitterService } from 'src/app/services/emitter.service';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {

  courses: Array<Course>;
  dataSource: MatTableDataSource<Course> = new MatTableDataSource();
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  displayedColumns: string[] = ['detail', 'title', 'author', 'category', 'action'];
  errorMessage: string;
  infoMessage: string;

  constructor(
    private courseService: CourseService,
    private router: Router,
    private route: ActivatedRoute,
    private emitterService: EmitterService
  ) {
    this.emitterService.onSearch
      .subscribe({
        next: (event: any) => {
          this.search(event.id, event.text);
        }
      }
    );
  }

  ngOnInit() {
    this.route.paramMap.subscribe(
      params => {
        let id;
        let text;
        if (params.has('id')) {
          id = params.get('id');
        }

        if (params.has('text')) {
          text = params.get('text')
        }
        this.search(id, text);
      }
    );
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  search(id, text) {
    if (id === '1') {
      this.findAllCourses();
    } else if (id === '2') {
      this.popularCourses();
    } else if (id === '3') {
      this.filterCourses(text);
    }
  }

  findAllCourses() {
    this.courseService.findAllCourses()
      .subscribe(
        data => {
          this.dataSource = new MatTableDataSource(data)
        }
      );
  }

  popularCourses() {
    this.courseService.popularCourses()
      .subscribe(
        data => this.dataSource = new MatTableDataSource(data)
    );
  }


  filterCourses(text: string) {
    this.courseService.filterCourses(text)
      .subscribe(
        data => this.dataSource = new MatTableDataSource(data)
    );
  }

  enroll(course: Course) {
    let transaction = new Transaction();
    transaction.course = course;
    this.courseService.enroll(transaction)
      .subscribe(
        data => {
          this.infoMessage = "You enrolled";
        },
        err => {
          this.errorMessage = "Unexpected Error";
        }
      );
  }

  detail(course: Course) {
    localStorage.setItem('currentCourse', JSON.stringify(course));
    this.router.navigate(['/course', course.id]);
  }
}
