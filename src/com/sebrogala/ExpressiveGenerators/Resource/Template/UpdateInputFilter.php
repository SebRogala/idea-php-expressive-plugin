<?php

namespace {{ ModuleName }}\InputFilter;

class Update{{ ResourceName }}InputFilter extends Create{{ ResourceName }}InputFilter
{
    public function __construct()
    {
        $this->add([
            'name' => 'id',
            'required' => true,
            'validators' => [
                [
                    'name' => 'Digits',
                ],
            ],
        ]);

        parent::__construct();
    }
}