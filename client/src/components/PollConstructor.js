import "./index.css";
import React, { useState } from "react";
import {
  Form,
  Button,
  Input,
  Checkbox,
  DatePicker,
  TimePicker,
  Slider,
  Row,
  Col,
  Space,
} from "antd";
import {
  PlusOutlined,
  PlusCircleOutlined,
  MinusCircleOutlined,
} from "@ant-design/icons";

const { RangePicker } = DatePicker;

const PollConstructor = () => {
  const [form] = Form.useForm();
  const onFinish = (values) => {};

  return (
    <div className="main">
      <h1>Poll constructor</h1>
      <Form
        className="main"
        form={form}
        name={"pollConstructor"}
        onFinish={onFinish}
      >
        <h2>Topic: </h2>
        <Form.Item
          name="text"
          lable="title"
          rules={[
            {
              required: true,
              message: "Missing title",
            },
          ]}
        >
          <Input.TextArea></Input.TextArea>
        </Form.Item>

        <div className="row-flex-box">
          <Form.List name="questions">
            {(fields, { add, remove }) => (
              <div>
                {fields.map((field) => (
                  <row key={field.key}>
                    <Form.Item
                      {...field}
                      label="Question text "
                      name={[(field.name, "text")]}
                    >
                      <Input.TextArea></Input.TextArea>
                    </Form.Item>
                  </row>
                ))}
                <Row align="midlle" justify="space-around">
                  <Form.Item>
                    <Button
                      type="dashed"
                      onClick={() => add()}
                      block
                      icon={<PlusOutlined />}
                    >
                      Add question
                    </Button>
                  </Form.Item>
                </Row>
              </div>
            )}
          </Form.List>
        </div>
      </Form>
    </div>
  );
};

export default PollConstructor;
