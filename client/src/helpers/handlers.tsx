import ReactDOM from "react-dom";
import ErrorDialog from "../Components/ErrorDialog/ErrorDialog";
import SuccessDialog from "../Components/SuccessDialog/SuccessDialog";

export const showSuccessModal = () => {
  const modalDiv = document.createElement("div");
  document.body.appendChild(modalDiv);

  const closeSuccessModal = () => {
    ReactDOM.unmountComponentAtNode(modalDiv);
    document.body.removeChild(modalDiv);
  };

  ReactDOM.render(<SuccessDialog onClose={closeSuccessModal} />, modalDiv);
};

export const showErrorModal = () => {
  const modalDiv = document.createElement("div");
  document.body.appendChild(modalDiv);

  const closeErrorModal = () => {
    ReactDOM.unmountComponentAtNode(modalDiv);
    document.body.removeChild(modalDiv);
  };

  ReactDOM.render(<ErrorDialog onClose={closeErrorModal} />, modalDiv);
};
