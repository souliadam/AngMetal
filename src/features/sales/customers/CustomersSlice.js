import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

export const getCustomersContent = createAsyncThunk('customers/content', async () => {
    const response = await axios.get('/api/customers');
    return response.data;
});

export const customerSlice = createSlice({
    name: 'customers',
    initialState: {
        isLoading: false,
        customers: [],
    },
    reducers: {
        addNewCustomer: (state, action) => {
            state.customers.push(action.payload.newCustomerObj);
        },
        deleteCustomer: (state, action) => {
            state.customers.splice(action.payload.index, 1);
        },
    },
    extraReducers: {
        [getCustomersContent.pending]: (state) => {
            state.isLoading = true;
        },
        [getCustomersContent.fulfilled]: (state, action) => {
            state.customers = action.payload.data;
            state.isLoading = false;
        },
        [getCustomersContent.rejected]: (state) => {
            state.isLoading = false;
        },
    },
});

export const { addNewCustomer, deleteCustomer } = customerSlice.actions;

export default customerSlice.reducer;
