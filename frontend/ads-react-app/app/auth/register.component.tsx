import { useState } from "react";
import { useNavigate } from "react-router";
import type { RegisterError, RegisterPayload } from "./auth.service";
import authService from "./auth.service";

// Assuming you saved RegisterError interface here

export default function Register() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState<RegisterPayload>({
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    phoneNumber: "",
    email: "",
    patientNo: "",
    dateOfBirth: "",
    address: { location: "" },
  });

  const [registerError, setRegisterError] = useState<RegisterError>({});

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    if (name === "location") {
      setFormData((prev) => ({
        ...prev,
        address: { ...prev.address, location: value },
      }));
    } else {
      setFormData((prev) => ({ ...prev, [name]: value }));
    }

    // Clear error as user types
    setRegisterError((prev) => ({ ...prev, [name]: undefined }));
  };

  const onRegisterPressed = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const result = await authService.registerAsNewPatient(formData);

    if (result.success) {
      navigate("/", { replace: true });
    } else if (result.errors) {
      setRegisterError(result.errors);
    }
  };

  const renderInput = (
    name: keyof RegisterPayload | "location",
    type: string,
    placeholder: string,
    value: string
  ) => {
    const inputName = name === "location" ? "address.location" : name;
    const errorMessage =
      registerError[inputName] ||
      (name === "location" && registerError["address"]);

    return (
      <>
        <input
          type={type}
          name={name}
          placeholder={placeholder}
          value={
            name === "location"
              ? formData.address.location
              : (formData[name as keyof RegisterPayload] as string)
          }
          onChange={onChange}
        />
        {errorMessage && <div style={{ color: "red" }}>{errorMessage}</div>}
        <br />
      </>
    );
  };

  return (
    <form>
      {renderInput("firstName", "text", "First Name", formData.firstName)}
      {renderInput("lastName", "text", "Last Name", formData.lastName)}
      {renderInput("username", "text", "Username", formData.username)}
      {renderInput("password", "password", "Password", formData.password)}
      {renderInput("phoneNumber", "text", "Phone Number", formData.phoneNumber)}
      {renderInput("email", "email", "Email", formData.email)}
      {renderInput("patientNo", "text", "Patient Number", formData.patientNo)}
      {renderInput(
        "dateOfBirth",
        "date",
        "Date of Birth",
        formData.dateOfBirth
      )}
      {renderInput(
        "location",
        "text",
        "Address Location",
        formData.address.location
      )}
      <button type="submit" onClick={onRegisterPressed}>
        Register
      </button>
    </form>
  );
}
