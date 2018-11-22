var app = angular.module('app', ['ui.grid','ui.grid.pagination']);
var filter = angular.module('filter', function() {
    return function() {
    }
});
app.controller('ContactListCtrl', ['$scope','ContactListService',
    function ($scope, ContactListService) {
        var paginationOptions = {
            pageNumber: 1,
            pageSize: 10,
            sort: {
                columnName: 'name',
                isAscending: true
            },
            filter: ''
        };

    ContactListService.list(
      paginationOptions.pageNumber,
      paginationOptions.pageSize,
      paginationOptions.sort.columnName,
      paginationOptions.sort.isAscending,
      ''
      ).success(function(data){
        $scope.gridOptions.data = data.content;
        $scope.gridOptions.totalItems = data.totalElements;
      });

    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
    $scope.gridOptions = {
        paginationPageSizes: [5, 10, 20],
        paginationPageSize: paginationOptions.pageSize,
        enableColumnMenus:false,
        useExternalPagination: true,
        useExternalSorting: true,
        enableFiltering: true,
        columnDefs: [
           { name: 'name', displayName: 'Person name' },
           { name: 'url' , displayName: 'Logo', cellTemplate:"<img width=50px ng-src='{{grid.getCellValue(row, col)}}' lazy-src>", enableSorting: false, enableFiltering: false},
        ],
        onRegisterApi: function(gridApi) {
           $scope.gridApi = gridApi;
           $scope.loadData = function() {
                ContactListService.list(
                  paginationOptions.pageNumber,
                  paginationOptions.pageSize,
                  paginationOptions.sort.columnName,
                  paginationOptions.sort.isAscending,
                  paginationOptions.filter
                  ).success(function(data){
                    $scope.gridOptions.data = data.content;
                    $scope.gridOptions.totalItems = data.totalElements;
                  });
           };
           gridApi.pagination.on.paginationChanged(
             $scope,
             function (newPage, pageSize) {
               paginationOptions.pageNumber = newPage;
               paginationOptions.pageSize = pageSize;
               $scope.loadData();
            });
            $scope.gridApi.core.on.sortChanged($scope, function(grid, sortColumns) {
                if (sortColumns.length == 0) {
                    paginationOptions.sort.columnName = '';
                    paginationOptions.sort.isAscending = true;
                } else {
                    paginationOptions.sort.columnName = sortColumns[0].name;
                    paginationOptions.sort.isAscending = sortColumns[0].sort.direction == 'asc' ? true : false;
                }
                $scope.loadData();
            });
            $scope.gridApi.core.on.filterChanged($scope, function() {
                if (this.grid.columns[0].filters[0].term.length > 2) {
                    paginationOptions.filter = this.grid.columns[0].filters[0].term != null ? this.grid.columns[0].filters[0].term : '';
                    $scope.loadData();
                }
            });
        }
    };
}]);

app.service('ContactListService',['$http', function ($http) {

    function list(pageNumber,size, sort, asc, filter) {
        pageNumber = pageNumber > 0 ? pageNumber - 1:0;
        return $http({
          method: 'GET',
            url: 'list/?page='+pageNumber+'&size='+size + '&field=name&asc=' + asc + '&filter=' + filter
        });
    }
    return {
        list: list
    };
}]);