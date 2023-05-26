import { useState, useEffect, useMemo } from 'react';
import PropTypes from 'prop-types';
import { alpha } from '@mui/material/styles';
import { makeStyles } from '@mui/styles';
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import TableSortLabel from '@mui/material/TableSortLabel';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
// import FormControlLabel from '@mui/material/FormControlLabel';
// import Switch from '@mui/material/Switch';
import DeleteIcon from '@mui/icons-material/Delete';
// import FilterListIcon from '@mui/icons-material/FilterList';
import { visuallyHidden } from '@mui/utils';
import TextField from '@mui/material/TextField';
// import AddIcon from '@mui/icons-material/Add';
import api from '../service/api';

const descendingComparator = (a, b, orderBy) => {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

const getComparator = (order, orderBy) => {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

const stableSort = (array, comparator) => {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) {
      return order;
    }
    return a[1] - b[1];
  });
  return stabilizedThis.map((el) => el[0]);
}

const headCells = [
  {
    id: 'userCode',
    numeric: true,
    disablePadding: false,
    label: '작업자코드',
  },
  {
    id: 'name',
    numeric: true,
    disablePadding: false,
    label: '이름',
  },
  {
    id: 'state',
    numeric: true,
    disablePadding: false,
    label: '상태',
  },
];

const useStyles = makeStyles((theme) => ({
  normalState: {
    color: 'black',
  },
  abnormalState: {
    color: 'red',
  },
}));

const EnhancedTableHead = (props) => {
  const { onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort } =
    props;
  const createSortHandler = (property) => (event) => {
    onRequestSort(event, property);
  };

  const classes = useStyles();

  return (
    <TableHead>
      <TableRow>
        <TableCell padding="checkbox">
          <Checkbox
            color="primary"
            indeterminate={numSelected > 0 && numSelected < rowCount}
            checked={rowCount > 0 && numSelected === rowCount}
            onChange={onSelectAllClick}
            inputProps={{
              'aria-label': 'select all desserts',
            }}
          />
        </TableCell>
        {headCells.map((headCell) => (
          <TableCell
            key={headCell.id}
            align={headCell.numeric ? 'center' : 'center'}
            padding={headCell.disablePadding ? 'none' : 'normal'}
            sortDirection={orderBy === headCell.id ? order : false}
            className={headCell.id === 'state' ? classes.columnHeader : null}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : 'asc'}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
              {orderBy === headCell.id ? (
                <Box component="span" sx={visuallyHidden}>
                  {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                </Box>
              ) : null}
            </TableSortLabel>
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}

EnhancedTableHead.propTypes = {
  numSelected: PropTypes.number.isRequired,
  onRequestSort: PropTypes.func.isRequired,
  onSelectAllClick: PropTypes.func.isRequired,
  order: PropTypes.oneOf(['asc', 'desc']).isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
};

const EnhancedTableToolbar = (props) => {
  const { numSelected, handleDelete } = props;

  return (
    <Toolbar
      sx={{
        pl: { sm: 2 },
        pr: { xs: 1, sm: 1 },
        ...(numSelected > 0 && {
          bgcolor: (theme) =>
            alpha(theme.palette.primary.main, theme.palette.action.activatedOpacity),
        }),
      }}
    >
      {numSelected > 0 ? (
        <Typography
          sx={{ flex: '1 1 100%' }}
          color="inherit"
          variant="subtitle1"
          component="div"
        >
          {numSelected} 선택
        </Typography>
      ) : (
        <Typography
          sx={{ flex: '1 1 100%' }}
          variant="h6"
          id="tableTitle"
          component="div"
        >
          투입 작업자 목록
        </Typography>
      )}

      {numSelected > 0 ? (
        <Tooltip title="Delete">
          <IconButton onClick={handleDelete}>
            <DeleteIcon />
          </IconButton>
        </Tooltip>
      ) : null}
    </Toolbar>
  );
}

EnhancedTableToolbar.propTypes = {
  numSelected: PropTypes.number.isRequired,
};

const TableUI = ({onValueChange}) => {
  const [order, setOrder] = useState('asc');
  const [orderBy, setOrderBy] = useState('useCode');
  const [selected, setSelected] = useState([]);
  const [page, setPage] = useState(0);
  const [dense, setDense] = useState(false);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [value, setValue] = useState('');
  const [searchValue, setSearchValue] = useState('');
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(true);

  const classes = useStyles();

  const token = sessionStorage.getItem('token');

  useEffect(() => {
    api.get('/worker/list', 
          {
              headers: {
              Authorization: `Bearer ${token}`
              }
          }
        )
        .then((response) => {
          const listdata = response.data;
          // console.log(listdata);
          const state = {
            1: '정상',
            2: '비정상',
            3: '정상',
          };
          const allrows = listdata.map((row)=>({
            ...row, state: state[row.userCode]
          }));
          // console.log(allrows);
          setRows(allrows);
          setLoading(false);
        })
        .catch((error) => {
          console.error(error);
        });
  }, [token]);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelected = rows.map((n) => n.userCode);
      setSelected(newSelected);
      return;
    }
    setSelected([]);
  };

  const handleClick = (event, userCode) => {
    const selectedIndex = selected.indexOf(userCode);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, userCode);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1),
      );
    }

    setSelected(newSelected);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  // const handleChangeDense = (event) => {
  //   setDense(event.target.checked);
  // };

  const handleDelete = () => {
    const updatedRows = rows.filter((row) => !selected.includes(row.userCode));
    setRows(updatedRows);
    setSelected([]);
    onValueChange(updatedRows);
  };

  // const handleInputAddChange = (event) => {
  //   setValue(event.target.value);
  // };

  const handleInputSearchChange = (event) => {
    setSearchValue(event.target.value);
  }

  // const handleInsert = () => {
  //   if (!value) {
  //     return;
  //   }

  //   const [name, calories, fat] = value.split(',')
  //   const newRow = createData(name, parseInt(calories), parseFloat(fat));
  //   const updatedRows = [...rows, newRow];
  //   setRows(updatedRows);
  //   setValue('');
  //   setSelected([]);
  // };

  const isSelected = (userCode) => selected.indexOf(userCode) !== -1;

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

  const visibleRows = useMemo(
    () =>
      stableSort(
        rows.filter((row) =>
          String(row.state).includes(searchValue)
        ),
        getComparator(order, orderBy)
      ).slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage),
    [rows, order, orderBy, page, rowsPerPage, searchValue],
  );
    
  useEffect(() => {
    if (!loading) {
      const tableData = () => {
        const data = visibleRows.map((row) => {
          // console.log(row.userCode);
          return row.userCode;
        });
        onValueChange(data);
      };

      tableData();
    }
  }, [loading, visibleRows]);

  return (
    <Box sx={{ width: '100%' }}>
        <Paper sx={{ width: '100%', mb: 2 }}>
            <EnhancedTableToolbar numSelected={selected.length} handleDelete={handleDelete} />
            <TableContainer component={Box} sx={{backgroundColor : '#FDF5E6'}}>
            <Table
                sx={{ minWidth: 550 }}
                aria-labelledby="tableTitle"
                size={dense ? 'small' : 'medium'}
            >
                <EnhancedTableHead
                numSelected={selected.length}
                order={order}
                orderBy={orderBy}
                onSelectAllClick={handleSelectAllClick}
                onRequestSort={handleRequestSort}
                rowCount={rows.length}
                />
                <TableBody>
                {visibleRows.map((row, index) => {
                    const isItemSelected = isSelected(row.userCode);
                    const labelId = `enhanced-table-checkbox-${index}`;

                    return (
                    <TableRow
                        hover
                        onClick={(event) => handleClick(event, row.userCode)}
                        role="checkbox"
                        aria-checked={isItemSelected}
                        tabIndex={-1}
                        key={row.userCode}
                        selected={isItemSelected}
                        sx={{ cursor: 'pointer' }}
                    >
                        <TableCell padding="checkbox">
                        <Checkbox
                            color="primary"
                            checked={isItemSelected}
                            inputProps={{
                            'aria-labelledby': labelId,
                            }}
                        />
                        </TableCell>
                        <TableCell
                        component="th"
                        id={labelId}
                        scope="row"
                        padding="none"
                        align='center'
                        sx={{ width: '40%' }}
                        >
                        {row.userCode}
                        </TableCell>
                        <TableCell align="center" sx={{ width: '30%' }}>{row.name}</TableCell>
                        <TableCell align="center" sx={{ width: '30%' }} className={row.state === '비정상' ? classes.abnormalState : classes.normalState}>{row.state}</TableCell>
                    </TableRow>
                    );
                })}
                {emptyRows > 0 && (
                    <TableRow
                    style={{
                        height: (dense ? 33 : 53) * emptyRows,
                    }}
                    >
                    <TableCell colSpan={6} />
                    </TableRow>
                )}
                </TableBody>
            </Table>
            </TableContainer>
            <TablePagination
            rowsPerPageOptions={[5, 10]}
            component="div"
            count={rows.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
            labelRowsPerPage="페이지당 인원"
            sx={{bgcolor : '#FDF5E6'}}
            />
        </Paper>
        {/* <FormControlLabel
            control={<Switch checked={dense} onChange={handleChangeDense} />}
            label="Dense padding"
        /> */}
        <TextField
          label="Search"
          placeholder="검색"
          value={searchValue}
          onChange={handleInputSearchChange}
          color='warning'
        />
    </Box>
  );
}

export default TableUI;