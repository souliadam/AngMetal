import moment from "moment";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import TitleCard from "../../../components/Cards/TitleCard";
import { openModal } from "../../common/modalSlice";
import { deleteCustomer, getCustomersContent } from "./CustomersSlice";
import { CONFIRMATION_MODAL_CLOSE_TYPES, MODAL_BODY_TYPES } from "../../../utils/globalConstantUtil";
import TrashIcon from '@heroicons/react/24/outline/TrashIcon';
import { showNotification } from "../../common/headerSlice";

const TopSideButtons = () => {
    const dispatch = useDispatch();

    const openAddNewCustomerModal = () => {
        dispatch(openModal({ title: "Add New Customer", bodyType: MODAL_BODY_TYPES.CUSTOMER_ADD_NEW }));
    };

    return (
        <div className="inline-block float-right">
            <button className="btn px-6 btn-sm normal-case btn-primary" onClick={openAddNewCustomerModal}>Add New</button>
        </div>
    );
};

function Customers() {
    const { customers } = useSelector(state => state.customer);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getCustomersContent());
    }, [dispatch]);

    const getDummyStatus = (index) => {
        if (index % 5 === 0) return <div className="badge">Not Interested</div>;
        else if (index % 5 === 1) return <div className="badge badge-primary">In Progress</div>;
        else if (index % 5 === 2) return <div className="badge badge-secondary">Sold</div>;
        else if (index % 5 === 3) return <div className="badge badge-accent">Need Followup</div>;
        else return <div className="badge badge-ghost">Open</div>;
    };

    const deleteCurrentCustomer = (index) => {
        dispatch(openModal({
            title: "Confirmation",
            bodyType: MODAL_BODY_TYPES.CONFIRMATION,
            extraObject: {
                message: `Are you sure you want to delete this customer?`,
                type: CONFIRMATION_MODAL_CLOSE_TYPES.CUSTOMER_DELETE,
                index
            }
        }));
    };

    return (
        <>
            <TitleCard title="Customers" topMargin="mt-2" TopSideButtons={<TopSideButtons />}>
                {/* Customers List in table format loaded from slice after api call */}
                <div className="overflow-x-auto w-full">
                    <table className="table w-full">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email Id</th>
                                <th>Created At</th>
                                <th>Status</th>
                                <th>Assigned To</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                customers.map((c, k) => {
                                    return (
                                        <tr key={k}>
                                            <td>
                                                <div className="flex items-center space-x-3">
                                                    <div className="avatar">
                                                        <div className="mask mask-squircle w-12 h-12">
                                                            <img src={c.avatar} alt="Avatar" />
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div className="font-bold">{c.first_name}</div>
                                                        <div className="text-sm opacity-50">{c.last_name}</div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>{c.email}</td>
                                            <td>{moment(new Date()).add(-5 * (k + 2), 'days').format("DD MMM YY")}</td>
                                            <td>{getDummyStatus(k)}</td>
                                            <td>{c.last_name}</td>
                                            <td><button className="btn btn-square btn-ghost" onClick={() => deleteCurrentCustomer(k)}><TrashIcon className="w-5" /></button></td>
                                        </tr>
                                    );
                                })
                            }
                        </tbody>
                    </table>
                </div>
            </TitleCard>
        </>
    );
}

export default Customers;
